/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.konan.objcexport

import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.SourceFile

sealed class Event {
    class TranslateClass(val declaration: ClassDescriptor) : Event()

    class TranslateInterface(val declaration: ClassDescriptor) : Event()

    class TranslateExtensions(
            val classDescriptor: ClassDescriptor,
            val declarations: List<CallableMemberDescriptor>
    ) : Event()

    class TranslateUnexposedClass(val classDescriptor: ClassDescriptor) : Event()

    class TranslateUnexposedInterface(val classDescriptor: ClassDescriptor) : Event()

    class TranslateFile(
            val sourceFile: SourceFile,
            val declarations: List<CallableMemberDescriptor>
    ) : Event()

    class TranslateClassForwardDeclaration(
            val classDescriptor: ClassDescriptor
    ) : Event()

    class TranslateInterfaceForwardDeclaration(
            val classDescriptor: ClassDescriptor
    ) : Event()
}
