/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package codegen.basics.unchecked_cast9

import kotlin.test.*

fun <T> foo(x: Int = 0): T = Any() as T
@Test
fun runTest() {
    try {
        println(foo<String>().length)
    } catch (e: ClassCastException) {
        println("Ok")
    }
}
