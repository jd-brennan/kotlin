/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package codegen.basics.unchecked_cast12

import kotlin.test.*

@Test
fun runTest() {
    try {
        println(("1" as Comparable<Any>).compareTo(2))
    } catch (e: ClassCastException) {
        println("Ok")
    }
}
