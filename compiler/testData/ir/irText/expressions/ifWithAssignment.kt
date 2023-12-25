// FIR_IDENTICAL

fun topLevelMethod() {
    var z = 1
    if(true) { z = 2 } else { z = 3 }
    if(true)   z = 4   else   z = 5

    when(z) {
        1 -> { z = 6 }
        else -> { z = 7 }
    }

    when(z) {
        1 -> z = 8
        else -> z = 9
    }
}