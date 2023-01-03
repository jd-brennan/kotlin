fun <T> builder(c: suspend () -> T): T = TODO()

class Test {
    fun doWork() {
        builder {
            execute {
                getData { getSomeString() }
            }
        }
    }

    private inline fun <T> execute(crossinline action: suspend () -> T): T = builder { action() }

    private suspend fun <T> getData(dataProvider: suspend () -> T): T = builder { dataProvider() }

    private suspend fun getSomeString(): String = "OK"
}

// JVM_TEMPLATES
// suspend lambdas: 4
// suspend lambdas $$forInline: 1
// 5 TABLESWITCH

// JVM_IR_TEMPLATES
// tail-call suspend lambdas: 4
// 0 TABLESWITCH