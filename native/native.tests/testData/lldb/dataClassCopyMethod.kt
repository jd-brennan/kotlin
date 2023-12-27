// KIND: STANDALONE_LLDB

fun main(args: Array<String>) {
    val point = Point(1, 2)
    val copied = point.copy(x = 3, y = 4)
    return
}

data class Point(val x: Int, val y: Int)
