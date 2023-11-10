// FIR_IDENTICAL
// OPT_IN: kotlin.native.internal.InternalForKotlinNative
import kotlinx.cinterop.*
import kotlinx.cinterop.internal.*

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
@CStruct(spelling = "struct { }") class Z constructor(rawPtr: NativePtr) : CStructVar(rawPtr) {
    val x: Pair<Int, Int>? = null
        @CStruct.MemberAt(offset = 0L) get
}

fun foo(z: Z) = z.x

fun main() { }
