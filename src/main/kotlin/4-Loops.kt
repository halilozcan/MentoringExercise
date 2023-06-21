fun main() {

    // for belirli adımlarda sayısal olarak gezinim için kullanılır
    val numbers = intArrayOf(1, 2, 3, 4, 5, 6)

    for (i in 0 until 10) {

    }

    for (i in 0 until 10 step 2) {

    }

    for (i in 10 downTo 0 step 2) {

    }

    for (i in 1..9) {

    }
    for (i in numbers.indices) {

    }

    for (i in 0 until numbers.size) {

    }

    numbers.forEach {

    }

    numbers.forEachIndexed { index, i ->

    }

    for ((index, value) in numbers.withIndex()) {

    }

    // while belirli bir şarta bağlı olarak döngü yapılacaksa kullanılır
    var x = 10
    while (x > 1) {
        x--
    }
    // kesinlikle bir defa çalıştırılır
    do {
        x--
    } while (x > 1)
}