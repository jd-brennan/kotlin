// COPY_RESOLUTION_MODE: PREFER_SELF

class Foo(private val name: String) {
    private fun foo() {
        nam<caret>e.length
    }
}