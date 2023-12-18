/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package codegen.bridges.test24

import kotlin.test.*

interface I<T> {
    fun foo(p: T)
}

class C : I<Nothing> {
    override fun foo(p: Nothing) {
        println(p)
    }
}

@Test fun runTest() {
    val c = C()
    assertFailsWith<ClassCastException> {
        (c as I<String>).foo("zzz")
    }
}
