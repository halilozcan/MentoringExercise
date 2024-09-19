package org.example

fun main() {
    val x = getValue()
    if (x is String) {
        println(x.length)
    }

    if (x !is String) {

    }

    processX(x)

    /** otomatik cast işlemi yapılmaz
    if(x is String || x.length){

    }
     **/

    // otomatik olarak cast eder
    if (x is String && x.length > 0) {

    }

    when (x) {
        is Int -> println(x + 1)
        is String -> println(x.length)
        is IntArray -> println(x.sum())
    }

    val y: String? = null
    // Null, nullable olmayan değere çevrilemez. Bu işlem unsafe bir cast işlemidir
    // val anotherX: String = y as String
    // val anotherXCast:String = y as String?

    val safeX: String? = y as? String

    /**
     * Smartcast Koşulları
     * val local değişkenler -> her zaman smart cast olabilirler (delegated properties haricinde)
     * val propertyler -> property private ya da internalsa veya check işlemi aynı module içerisinde yapılıyorsa
     * veya custom getter yoksa smart cast yapılabilir.
     * var local değişkenler -> kullanım ve kontrol aşamasında değiştirilme yoksa veya kendini değiştiren bir lambda
     * yoksa ve local delegated property değilse smart cast yapılabilir.
     * var property -> asla yapılmaz, çünkü her zaman değiştirilebilirler
     */
}

fun getValue(): Any {
    return "Halil"
}

fun processX(x: Any) {
    if (x !is String) {
        return
    }

    println(x.length)
}