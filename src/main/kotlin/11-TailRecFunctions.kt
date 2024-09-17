fun main() {

}

/**
 * Bazen algoritmalar recursive çözüm sunarlar. Bu recursive çözümlerde fonksiyon çağırılması
 * bazen stackoverflow a sebep olabilir. Bundan dolayı recursive yerine imperative şekilde
 * algoritmanın değişmesi gerekebilir. bunun için kotlin de tailrec fonksiyon verilebilir.
 * tailrec fonksiyondaki tek kural recursive çağırımın son çağırım olması gereklidir.
 */

fun recursiveFactorial(n: Long): Long {
    return if (n <= 1) {
        n
    } else {
        n * recursiveFactorial(n - 1)
    }
}

// yukarıdaki fonksiyonda recursive çağırım son çağırım değildir. n * recursiveFactorial(n - 1)

// buradaki durumda recursive çağırım son çağırım olur.
fun factorial(n: Long, accum: Long = 1): Long {
    val soFar = n * accum
    return if (n <= 1) {
        soFar
    } else {
        factorial(n - 1, soFar)
    }
}

tailrec fun factorialTailRec(n: Long, accum: Long = 1): Long {
    val soFar = n * accum
    return if (n <= 1) {
        soFar
    } else {
        factorialTailRec(n - 1, soFar)
    }
}