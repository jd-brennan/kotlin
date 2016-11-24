// WITH_RUNTIME
// WITH_REFLECT
// CHECK_NOT_CALLED: suspendInline_die06n$
// CHECK_NOT_CALLED: suspendInline_nesahw$
// CHECK_NOT_CALLED: suspendInline_grpnnl$
class Controller {
    fun withValue(v: String, x: Continuation<String>) {
        x.resume(v)
    }

    suspend inline fun suspendInline(v: String): String = suspendWithCurrentContinuation { x ->
        withValue(v, x)
    }

    suspend inline fun suspendInline(crossinline b: () -> String): String = suspendInline(b())

    suspend inline fun <reified T : Any> suspendInline(): String = suspendInline({ T::class.simpleName!! })

    // INTERCEPT_RESUME_PLACEHOLDER
}

fun builder(coroutine c: Controller.() -> Continuation<Unit>) {
    c(Controller()).resume(Unit)
}

class OK

fun box(): String {
    var result = ""

    builder {
        result = suspendInline("56")
        if (result != "56") throw RuntimeException("fail 1")

        result = suspendInline { "57" }
        if (result != "57") throw RuntimeException("fail 2")

        result = suspendInline<OK>()
    }

    return result
}
