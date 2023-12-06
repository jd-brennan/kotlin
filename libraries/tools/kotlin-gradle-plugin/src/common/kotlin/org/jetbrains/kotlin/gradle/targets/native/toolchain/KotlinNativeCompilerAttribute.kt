/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.native.toolchain

import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributesSchema

/**
 * This class provides functionality for setting up attributes matching strategy and
 * transformation for a Kotlin Native Compiler configurations.
 *
 * @property attribute The attribute object representing the Kotlin Native compiler type.
 */
internal object KotlinNativeCompilerAttribute {

    val attribute: Attribute<KotlinNativeCompilerArtifactsTypes> =
        Attribute.of("kotlin.native.compiler.type", KotlinNativeCompilerArtifactsTypes::class.java)

    internal enum class KotlinNativeCompilerArtifactsTypes {
        ARCHIVE,
        DIRECTORY
    }

    /**
     * Sets up the attributes matching strategy for the given attributes schema.
     *
     * @param attributesSchema The attributes schema to set up the matching strategy for.
     */
    internal fun setupAttributesMatchingStrategy(attributesSchema: AttributesSchema) {
        attributesSchema.attribute(attribute)
    }

    /**
     * Sets up the necessary transformations for handling artifact types "tar.gz" and "zip" in the given project.
     *
     * @param project The project in which to set up the transformations.
     */
    internal fun setupTransform(project: Project) {
        project.dependencies.artifactTypes.maybeCreate("tar.gz").also { artifactType ->
            artifactType.attributes.attribute(attribute, KotlinNativeCompilerArtifactsTypes.ARCHIVE)
        }

        project.dependencies.artifactTypes.maybeCreate("zip").also { artifactType ->
            artifactType.attributes.attribute(attribute, KotlinNativeCompilerArtifactsTypes.ARCHIVE)
        }

        project.dependencies.registerTransform(UnzipTransformationAction::class.java) { transform ->
            transform.from.attribute(attribute, KotlinNativeCompilerArtifactsTypes.ARCHIVE)
            transform.to.attribute(attribute, KotlinNativeCompilerArtifactsTypes.DIRECTORY)
        }
    }
}