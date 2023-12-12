// WITH_STDLIB
// SKIP_KLIB_TEST
// IGNORE_BACKEND_K1: JS_IR

// KT-64271
// IGNORE_BACKEND_K2: NATIVE
// IGNORE_BACKEND_K2: WASM
// IGNORE_BACKEND_K2: JS, JS_IR, JS_IR_ES6


sealed class A : CharSequence {
    data class B(val c: CharSequence) : A(), CharSequence by c
}
