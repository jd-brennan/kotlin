/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.bir.symbols.impl

import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI
import org.jetbrains.kotlin.bir.declarations.BirExternalPackageFragment
import org.jetbrains.kotlin.bir.symbols.BirExternalPackageFragmentSymbol
import org.jetbrains.kotlin.ir.util.IdSignature

@OptIn(ObsoleteDescriptorBasedAPI::class)
class DescriptorlessExternalPackageFragmentSymbol : BirExternalPackageFragmentSymbol {
    override val descriptor: PackageFragmentDescriptor
        get() = error("Operation is unsupported")

    override val hasDescriptor: Boolean
        get() = error("Operation is unsupported")

    private var _owner: BirExternalPackageFragment? = null
    override val owner get() = _owner!!

    override val signature: IdSignature?
        get() = TODO("Not yet implemented")

    override val isBound get() = _owner != null

    override fun bind(owner: BirExternalPackageFragment) {
        _owner = owner
    }

    override var privateSignature: IdSignature? = null
}
