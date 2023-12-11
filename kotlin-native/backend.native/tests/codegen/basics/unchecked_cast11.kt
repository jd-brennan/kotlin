/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package codegen.basics.unchecked_cast11

import kotlin.test.*

fun foo(x: Int) = x + 42

@Test
fun runTest() {
    val arr = arrayOf("zzz")
    try {
        println(foo((arr as Array<Int>)[0]))
    } catch (e: ClassCastException) {
        println("Ok")
    }
}
