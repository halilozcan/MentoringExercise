fun main() {
    val a = 10
    var max = a
    val b = 15

    /** İf - else */
    if (a < b) max = b

    if (a > b) {
        max = a
    } else {
        max = b
    }

    // Single Expression
    max = if (a > b) a else b

    max = if (a > b) {
        // Değer dönmeden önce işlemler de yapılabilir
        a
    } else if (b > a) {
        b
    } else {
        a
    }

    /** When */
    when (max) {
        1 -> println("1")
        2 -> println("2")
        else -> println("other than 1 or 2")
    }

    // Eğer bütün durumlar koyulduysa else yazmaya gerek yoktur
    val index = when (Color.valueOf("BLUE")) {
        Color.BLUE -> 0
        Color.RED -> 1
    }

    // Else burada tüm durumlar olmadığı için konulması zorunludur
    val newIndex = when (Color.valueOf("BLUE")) {
        Color.BLUE -> 0
        else -> Int.MIN_VALUE
    }

    // Birden fazla şartı virgüller ayırabilirsiniz
    when (max) {
        1, 2 -> println("1 or 2")
        else -> println("other than 1 or 2")
    }

    when (max) {
        1L.toInt(), 2f.toInt() -> println("1 or 2")
        else -> println("other than 1 or 2")
    }

    // when in parametre olmadan kullanılışı
    max = when {
        a > b -> a
        b > a -> b
        else -> a
    }


}

enum class Color {
    BLUE,
    RED
}