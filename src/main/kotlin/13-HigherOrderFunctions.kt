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

    doOperation(
        4,
        5,
        { a, b ->
            a + b
        })
    { a, b ->
        a * b
    }

    // birden fazla lambda expression varsa named argument ile bu durum çözülebilir
    doOperation(number1 = 4,
        number2 = 5,
        operation1 = { a, b ->
            a + b
        },
        operation2 = { a, b ->
            a * b
        })

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

    rollDice(
        range = 1..6,
        time = 3,
        operationInformer = {
            println(it)
        },
        callback = {
            println("You rolled:$it")
        })

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
fun rollDice(range: IntRange, time: Int, operationInformer: (String) -> Unit, callback: (result: Int) -> Unit) {
    operationInformer.invoke("Operation Started")
    for (i in 0 until time) {
        operationInformer.invoke("User Rolling Dice")
        val result = range.random()
        operationInformer.invoke("User Rolled")
        callback(result)
    }
    operationInformer.invoke("Operation Finished")
}

fun getZero(): Float = 0.0f

fun getZero(number: Int) = if (number > 0) number.toFloat() else getZero()

fun doOperation(number1: Int, number2: Int, operation1: (Int, Int) -> Int, operation2: (Int, Int) -> Int) {
    operation1.invoke(number1, number2)
    operation2.invoke(number1, number2)
}