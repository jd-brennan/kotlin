/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.plugin.internal

import org.gradle.api.attributes.Attribute
import org.gradle.api.attributes.AttributeContainer
import org.gradle.api.provider.ProviderFactory

internal class AttributesCopyHelperG6 : AttributesCopyHelper {
    override fun <T : Any> copyAttribute(
        from: AttributeContainer,
        to: AttributeContainer,
        key: Attribute<T>
    ) {
        to.attribute(key, from.getAttribute(key)!!)
    }
}

internal class AttributeCopyHelperVariantFactoryG6 : AttributesCopyHelper.AttributeCopyHelperVariantFactory {
    override fun getInstance(
        providerFactory: ProviderFactory
    ): AttributesCopyHelper = AttributesCopyHelperG6()
}
