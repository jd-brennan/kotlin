/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.objcexport.tests

import org.jetbrains.kotlin.analysis.api.analyze
import org.jetbrains.kotlin.analysis.api.symbols.KtNamedClassOrObjectSymbol
import org.jetbrains.kotlin.backend.konan.objcexport.ObjCExportClassOrProtocolName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.objcexport.ObjCExportNamer
import org.jetbrains.kotlin.objcexport.testUtils.InlineSourceCodeAnalysis
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class KtObjCExportNamerTest(
    private val inlineSourceCodeAnalysis: InlineSourceCodeAnalysis,
) {

    private val namer = ObjCExportNamer()

    @Test
    fun `test - simple class`() {
        val foo = inlineSourceCodeAnalysis.createKtFile("class Foo")
        analyze(foo) {
            val fooSymbol = foo.getFileSymbol().getFileScope()
                .getClassifierSymbols(Name.identifier("Foo"))
                .single() as KtNamedClassOrObjectSymbol

            assertEquals(
                ObjCExportClassOrProtocolName("Foo", "Foo"),
                namer.getClassOrProtocolName(fooSymbol)
            )
        }
    }
}
