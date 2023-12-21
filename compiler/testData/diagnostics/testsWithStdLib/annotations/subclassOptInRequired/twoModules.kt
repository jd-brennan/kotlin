// MODULE: a
// FILE: a.kt
@RequiresOptIn
annotation class Ann

@SubclassOptInRequired(Ann::class)
open class ClassFromModuleOne {
}

// MODULE: b(a)
// FILE: b.kt
class Two() : ClassFromModuleOne()

fun main() {
    Two()
}