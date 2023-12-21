/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.plugin.internal

import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributeContainer
import org.gradle.api.provider.ProviderFactory
import org.jetbrains.kotlin.gradle.plugin.VariantImplementationFactories
import org.jetbrains.kotlin.gradle.plugin.variantImplementationFactory

/**
 * Gradle 7.4 has introduced a way configuring attribute using provider. Since Gradle 8.3 using eager way to configure attribute causes
 * eager task realization (see KT-60664).
 */
interface AttributesCopyHelper {
    fun <T : Any> copyAttribute(
        from: AttributeContainer,
        to: AttributeContainer,
        key: Attribute<T>
    )

    fun copyAttributes(
        from: AttributeContainer,
        to: AttributeContainer,
        keys: Iterable<Attribute<*>> = from.keySet()
    ) {
        for (key in keys) {
            copyAttribute(from, to, key)
        }
    }

    interface AttributeCopyHelperVariantFactory : VariantImplementationFactories.VariantImplementationFactory {
        fun getInstance(providerFactory: ProviderFactory): AttributesCopyHelper
    }
}

internal class DefaultAttributesCopyHelper(
    private val providerFactory: ProviderFactory
) : AttributesCopyHelper {
    override fun <T : Any> copyAttribute(
        from: AttributeContainer,
        to: AttributeContainer,
        key: Attribute<T>
    ) {
        to.attributeProvider(key, providerFactory.provider { from.getAttribute(key) })
    }
}

internal class DefaultAttributeCopyHelperVariantFactory : AttributesCopyHelper.AttributeCopyHelperVariantFactory {
    override fun getInstance(
        providerFactory: ProviderFactory
    ): AttributesCopyHelper = DefaultAttributesCopyHelper(providerFactory)
}

internal val Project.attributesCopyHelper
    get() = variantImplementationFactory<AttributesCopyHelper.AttributeCopyHelperVariantFactory>()
        .getInstance(providers)
