/*
 * Copyright 2010-2023 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package codegen.basics.unchecked_cast10

import kotlin.test.*

val any = Any()
fun <T> foo(): T = any as T

@Test
fun runTest() {
    val unit1: Any = foo<Unit>()
    val unit2 = foo<Unit>()
    assertEquals(Unit, unit1)
    assertEquals(Unit, unit2)
}
