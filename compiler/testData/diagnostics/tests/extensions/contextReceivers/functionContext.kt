// ISSUE: KT-61937
// !LANGUAGE: +ContextReceivers

class Ctx
fun Ctx.context() {}

context(Ctx)
fun foo(body: Ctx.() -> Unit) {
    context()
    body<!NO_VALUE_FOR_PARAMETER!>()<!>
}

context(Ctx)
fun bar(context: Ctx.() -> Unit) {
    context()
}
