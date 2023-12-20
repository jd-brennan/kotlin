/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.plugin.mpp

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.jetbrains.kotlin.commonizer.toolsDir
import org.jetbrains.kotlin.gradle.plugin.KotlinProjectSetupCoroutine
import org.jetbrains.kotlin.gradle.targets.native.internal.konanDistribution
import org.jetbrains.kotlin.gradle.tasks.locateOrRegisterTask
import org.jetbrains.kotlin.gradle.utils.fileProperty
import org.jetbrains.kotlin.gradle.utils.getFile
import java.io.File
import javax.inject.Inject

internal val KotlinLLDBScriptSetupAction = KotlinProjectSetupCoroutine {
    locateOrRegisterLLDBScriptTask()
}

abstract class LLDBInitTask
@Inject constructor(
    private val objects: ObjectFactory,
    private val projectLayout: ProjectLayout,
) : DefaultTask() {

    @get:Input
    internal abstract val fileName: Property<String>

    @get:Input
    internal abstract val konanToolsDir: Property<File>

    @get:OutputFile
    val outputFile: File
        get() = projectLayout.buildDirectory.getFile().resolve(fileName.get())

    @TaskAction
    fun createScript() {
        projectLayout
            .file(objects.fileProperty(outputFile).asFile)
            .getFile()
            .writeText("command script import ${konanToolsDir.get().resolve("konan_lldb.py")}")
    }
}

internal fun Project.locateOrRegisterLLDBScriptTask(): TaskProvider<LLDBInitTask> {
    return locateOrRegisterTask("setupLLDBScript") { task ->
        task.description = "Generate lldbinit file with imported konan_lldb.py script"
        task.fileName.set("lldbinit")
        task.konanToolsDir.set(konanDistribution.toolsDir)
    }
}