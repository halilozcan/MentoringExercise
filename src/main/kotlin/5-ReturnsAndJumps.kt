fun main() {
    val name: String? = null

    val nameLength = name?.length ?: return

    println("Reached here")

    // expressionlar label ile işaretlenebilir. bunun için sonuna @ eklenerek isimlendirme yapılır

    sampleLoop@ for (i in 1..10) {

    }

    // dışarıdaki döngüyü bitirir.
    outerLoop@ for (i in 1..10) {
        innerLoop@ for (j in 1..10) {
            if (i == 5) {
                break@outerLoop
            }
            //println("i:$i j:$j")
        }
    }

    intArrayOf(1, 2, 3, 4, 5).forEach {
        //if (it == 3) return
        // buradaki return fonksiyonun çalışmasını bitirir
        //println(it)
    }

    // lambda fonksiyonunun o adımı atlamasın için kullanılır
    intArrayOf(1, 2, 3, 4, 5).forEach lambda@{
        if (it == 3) return@lambda
        println(it)
    }

    // direkt fonksiyona dönüş için kullanılır
    run loop@{
        intArrayOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop
            println(it)
        }
    }
}