/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.wasm.utils

import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.backend.js.SourceMapsInfo
import org.jetbrains.kotlin.js.sourceMap.SourceFilePathResolver
import org.jetbrains.kotlin.js.sourceMap.SourceMap3Builder
import org.jetbrains.kotlin.wasm.ir.convertors.DebuggableTargetGenerator
import org.jetbrains.kotlin.wasm.ir.source.location.DebugInformationGenerator
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocation
import org.jetbrains.kotlin.wasm.ir.source.location.SourceLocationMapping
import java.io.File

class SourceMapGenerator(
    baseFileName: String,
    private val configuration: CompilerConfiguration
) : DebugInformationGenerator {
    // TODO: eliminate duplication for the [org.jetbrains.kotlin.backend.wasm.writeCompilationResult] logic
    private val sourceMapFileName = "$baseFileName.map"
    private val sourceLocationMappings = mutableListOf<SourceLocationMapping>()

    override fun addLocation(location: SourceLocationMapping) {
        sourceLocationMappings.add(location)
    }

    override fun appendDebugInformation(targetGenerator: DebuggableTargetGenerator) {
        targetGenerator.appendCustomSection {
            appendString("sourceMappingURL")
            appendString(sourceMapFileName)
        }
    }

    fun generate(): String? {
        val sourceMapsInfo = SourceMapsInfo.from(configuration) ?: return null

        val sourceMapBuilder =
            SourceMap3Builder(null, { error("This should not be called for Kotlin/Wasm") }, sourceMapsInfo.sourceMapPrefix)

        val pathResolver =
            SourceFilePathResolver.create(sourceMapsInfo.sourceRoots, sourceMapsInfo.sourceMapPrefix, sourceMapsInfo.outputDir)

        var prev: SourceLocation? = null
        var prevGeneratedLine = 0

        for (mapping in sourceLocationMappings) {
            val generatedLocation = mapping.generatedLocation

            if (prevGeneratedLine != generatedLocation.line) {
                sourceMapBuilder.newLine()
                prevGeneratedLine = generatedLocation.line
            }

            when (val sourceLocation = mapping.sourceLocation.takeIf { it != prev } ?: continue) {
                is SourceLocation.NoLocation -> sourceMapBuilder.addEmptyMapping(generatedLocation.column)
                is SourceLocation.Location -> {
                    sourceLocation.apply {
                        // TODO resulting path goes too deep since temporary directory we compiled first is deeper than final destination.
                        val relativePath = pathResolver.getPathRelativeToSourceRoots(File(file)).replace(Regex("^\\.\\./"), "")
                        sourceMapBuilder.addMapping(relativePath, null, { null }, line, column, null, generatedLocation.column)
                        prev = this
                    }
                }
            }

        }

        return sourceMapBuilder.build()
    }
}