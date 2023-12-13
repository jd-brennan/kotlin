// FILE: test.kt
class Foo {
    inner class Bar {
    }
}

fun box() {
    val x = Foo()
    x.Bar()
}

// EXPECTATIONS JVM JVM_IR JVM_IR +USE_INLINE_SCOPES_NUMBERS
// test.kt:8 box:
// test.kt:2 <init>:
// test.kt:8 box:
// test.kt:9 box: x:Foo=Foo
// test.kt:3 <init>:
// test.kt:9 box: x:Foo=Foo
// test.kt:10 box: x:Foo=Foo

// EXPECTATIONS JS_IR
// test.kt:8 box:
// test.kt:2 <init>:
// test.kt:9 box: x=Foo
// test.kt:3 <init>:
// test.kt:3 <init>:
// test.kt:10 box: x=Foo
