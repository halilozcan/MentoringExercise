fun main() {
    // lambda expression kullanımı
    higherOrderFunc(4, 5) { a, b ->
        a * b
    }

    // fonksiyon gönderme
    higherOrderFunc(4, 3, ::sumHigher)

    val zero: () -> Float = ::getZero

    zero.invoke()

    val zeroArgument: (Int) -> Float = ::getZero

    val result = zeroArgument(4)


    // Higher order fonksiyonlar tek parametreli olduğu zaman it identifier ile temsil edilirler
    printSentence("Hello") {
        println(it)
    }

    val message = returnLambda()

    println(message())

    /**
     * Fonksiyonlar ve higher orderlar bu şekilde çağırılabilir. invoke() un olmasının
     * temel nedenlerinden birisi lambda expression ın null olabilme durumudur.
     */
    println(message.invoke())

    // Anonymous fonksiyonlar higher order fonksiyonlara gönderilebilir
    val add = fun(a: Int, b: Int): Int {
        return a + b
    }

    additionAnonymous(add)

    /**
     * Eğer higher order fonksiyona parametre olarak gelen fonksiyon son parametre ise
     * bu fonksiyon fonksiyon parantezlerini dışına süslü parantezler konularak çağırılabilir
     */

    rollDice(1..6, 3) {

    }

}

/**
 * Higher order fonksiyonlar parametre olarak fonksiyon veya lambda expressions alırlar.
 * Higher order kullanımında bir tane fonksiyon nesnesi üretilir.
 */


fun higherOrderFunc(a: Int, b: Int, operation: ((Int, Int) -> Int)) {
    println("result:${operation(a, b)}")
}

fun sumHigher(a: Int, b: Int): Int = a + b

fun printSentence(sentence: String, print: (String) -> Unit) {
    print(sentence)
}

// Lambda expressionlar değer olarak döndürebilir.
fun returnLambda(): () -> String {
    val lambda = { "Hi" }
    return lambda
}

fun additionAnonymous(addition: ((Int, Int) -> Int)?) {
    val result = addition?.invoke(10, 20)
    println(result)
}

// Fonksiyon parametrelerini isimlendirebiliriz
fun rollDice(range: IntRange, time: Int, callback: (result: Int) -> Unit) {
    for (i in 0 until time) {
        val result = range.random()
        callback(result)
    }
}

fun getZero(): Float = 0.0f

fun getZero(number: Int) = if (number > 0) number.toFloat() else getZero()
/**
 *
 */




