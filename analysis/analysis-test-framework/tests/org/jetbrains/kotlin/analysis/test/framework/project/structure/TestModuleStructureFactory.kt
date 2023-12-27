/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.test.framework.project.structure

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.analysis.api.standalone.base.project.structure.KtModuleProjectStructure
import org.jetbrains.kotlin.analysis.api.standalone.base.project.structure.KtModuleWithFiles
import org.jetbrains.kotlin.analysis.api.standalone.base.project.structure.StandaloneProjectFactory
import org.jetbrains.kotlin.analysis.project.structure.KtBinaryModule
import org.jetbrains.kotlin.analysis.project.structure.KtNotUnderContentRootModule
import org.jetbrains.kotlin.analysis.test.framework.services.environmentManager
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.js.config.JSConfigurationKeys
import org.jetbrains.kotlin.library.KLIB_FILE_EXTENSION
import org.jetbrains.kotlin.platform.TargetPlatform
import org.jetbrains.kotlin.platform.js.JsPlatforms
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms
import org.jetbrains.kotlin.platform.jvm.isJvm
import org.jetbrains.kotlin.test.model.DependencyRelation
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.*
import org.jetbrains.kotlin.test.services.configuration.JvmEnvironmentConfigurator
import org.jetbrains.kotlin.test.util.KtTestUtil
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension

object TestModuleStructureFactory {
    fun createProjectStructureByTestStructure(
        moduleStructure: TestModuleStructure,
        testServices: TestServices,
        project: Project
    ): KtModuleProjectStructure {
        val moduleEntries = moduleStructure.modules.map { testModule ->
            testServices.getKtModuleFactoryForTestModule(testModule).createModule(testModule, testServices, project)
        }

        val moduleEntriesByName = moduleEntries.associateByName()

        val libraryCache = mutableMapOf<Set<Path>, KtBinaryModule>()

        for (testModule in moduleStructure.modules) {
            val moduleWithFiles = moduleEntriesByName[testModule.name] ?: moduleEntriesByName.getValue(testModule.files.single().name)
            when (val ktModule = moduleWithFiles.ktModule) {
                is KtNotUnderContentRootModule -> {
                    // Not-under-content-root modules have no external dependencies on purpose
                }
                is KtModuleWithModifiableDependencies -> {
                    addModuleDependencies(testModule, moduleEntriesByName, ktModule)
                    addLibraryDependencies(testModule, testServices, project, ktModule, libraryCache::getOrPut)
                }
                else -> error("Unexpected module type: " + ktModule.javaClass.name)
            }
        }

        return KtModuleProjectStructure(moduleEntries, libraryCache.values)
    }

    private fun addModuleDependencies(
        testModule: TestModule,
        moduleByName: Map<String, KtModuleWithFiles>,
        ktModule: KtModuleWithModifiableDependencies
    ) {
        testModule.allDependencies.forEach { dependency ->
            val dependencyKtModule = moduleByName.getValue(dependency.moduleName).ktModule
            when (dependency.relation) {
                DependencyRelation.RegularDependency -> ktModule.directRegularDependencies.add(dependencyKtModule)
                DependencyRelation.FriendDependency -> ktModule.directFriendDependencies.add(dependencyKtModule)
                DependencyRelation.DependsOnDependency -> ktModule.directDependsOnDependencies.add(dependencyKtModule)
            }
        }
    }

    private fun addLibraryDependencies(
        testModule: TestModule,
        testServices: TestServices,
        project: Project,
        ktModule: KtModuleWithModifiableDependencies,
        libraryCache: (paths: Set<Path>, factory: () -> KtBinaryModule) -> KtBinaryModule
    ) {
        val compilerConfiguration = testServices.compilerConfigurationProvider.getCompilerConfiguration(testModule)

        val classpathRoots = compilerConfiguration[CLIConfigurationKeys.CONTENT_ROOTS, emptyList()]
            .mapNotNull { (it as? JvmClasspathRoot)?.file?.toPath() }

        if (classpathRoots.isNotEmpty()) {
            val jdkKind = JvmEnvironmentConfigurator.extractJdkKind(testModule.directives)
            val jdkHome = JvmEnvironmentConfigurator.getJdkHome(jdkKind)?.toPath()
                ?: JvmEnvironmentConfigurator.getJdkClasspathRoot(jdkKind)?.toPath()
                ?: Paths.get(System.getProperty("java.home"))

            val (jdkRoots, libraryRoots) = classpathRoots.partition { jdkHome != null && it.startsWith(jdkHome) }

            if (testModule.targetPlatform.isJvm() && jdkRoots.isNotEmpty()) {
                val jdkModule = libraryCache(jdkRoots.toSet()) {
                    val jdkScope = getScopeForLibraryByRoots(jdkRoots, testServices)
                    KtJdkModuleImpl("jdk", JvmPlatforms.defaultJvmPlatform, jdkScope, project, jdkRoots)
                }
                ktModule.directRegularDependencies.add(jdkModule)
            }

            for (libraryRoot in libraryRoots) {
                check(libraryRoot.extension == "jar")

                val libraryModule = libraryCache(setOf(libraryRoot)) {
                    createLibraryModule(project, libraryRoot, JvmPlatforms.defaultJvmPlatform, testServices)
                }

                ktModule.directRegularDependencies.add(libraryModule)
            }
        }

        val jsLibraryRootPaths = compilerConfiguration[JSConfigurationKeys.LIBRARIES].orEmpty()

        if (jsLibraryRootPaths.isNotEmpty()) {
            for (libraryRootPath in jsLibraryRootPaths) {
                val libraryRoot = Paths.get(libraryRootPath)
                check(libraryRoot.extension == KLIB_FILE_EXTENSION)

                val libraryModule = libraryCache(setOf(libraryRoot)) {
                    createLibraryModule(project, libraryRoot, JsPlatforms.defaultJsPlatform, testServices)
                }

                ktModule.directRegularDependencies.add(libraryModule)
            }
        }
    }

    private fun createLibraryModule(
        project: Project,
        libraryFile: Path,
        platform: TargetPlatform,
        testServices: TestServices,
    ): KtLibraryModuleImpl {
        check(libraryFile.exists()) { "Library $libraryFile does not exist" }

        val libraryName = libraryFile.nameWithoutExtension
        val libraryScope = getScopeForLibraryByRoots(listOf(libraryFile), testServices)
        return KtLibraryModuleImpl(libraryName, platform, libraryScope, project, listOf(libraryFile), librarySources = null)
    }

    fun getScopeForLibraryByRoots(roots: Collection<Path>, testServices: TestServices): GlobalSearchScope {
        return StandaloneProjectFactory.createSearchScopeByLibraryRoots(
            roots,
            testServices.environmentManager.getProjectEnvironment()
        )
    }

    fun createSourcePsiFiles(
        testModule: TestModule,
        testServices: TestServices,
        project: Project,
    ): List<PsiFile> {
        return testModule.files.map { testFile ->
            when {
                testFile.isKtFile -> {
                    val fileText = testServices.sourceFileProvider.getContentOfSourceFile(testFile)
                    KtTestUtil.createFile(testFile.name, fileText, project)
                }

                testFile.isJavaFile || testFile.isExternalAnnotation -> {
                    val filePath = testServices.sourceFileProvider.getRealFileForSourceFile(testFile)
                    val virtualFile =
                        testServices.environmentManager.getApplicationEnvironment().localFileSystem.findFileByIoFile(filePath)
                            ?: error("Virtual file not found for $filePath")
                    PsiManager.getInstance(project).findFile(virtualFile)
                        ?: error("PsiFile file not found for $filePath")
                }

                else -> error("Unexpected file ${testFile.name}")
            }
        }
    }
}

