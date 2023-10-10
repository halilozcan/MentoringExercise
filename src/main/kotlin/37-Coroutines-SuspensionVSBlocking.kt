import kotlinx.coroutines.*

fun main() {
    updateRemainingTimeThread(5)
}


/**-------------------- BLOCKING --------------------*/
private fun updateRemainingTimeThread(remainingTimeSeconds: Int) {
    for (time in remainingTimeSeconds downTo 0) {
        if (time > 0) {
            println("updateRemainingTime: $time seconds")
            Thread.sleep(1000)
        } else {
            println("else")
        }
    }
}

/**
 * Şimdi burada delay diyerek olayı 1 saniyeliğine durduruyorum. Bu delay işlemini yaparken ben UI Thread'deyim. Burda
 * bekliyoru olduğum şey 1 saniyelik bu bekleme kısmında UI'ın blocklanması. updateRemainingTimeThread() fonksiyonu çalıştığında
 * olacak şey tam da bu. Bu olaya blocking diyoruz.
 *
 * Delay kullandığımda UI nasıl blocklanmıyor peki? Biz Coroutine ile birlikte bir Thread üzerinde concurrency oluşturabiliyoruz.
 * Bu standart JVM Threads'lerinin yapamayacağı bir şey ve Coroutine ile JVM Threads arasındaki büyük farklardan biri.
 * */

/**-------------------- SUSPENSION --------------------*/
private suspend fun updateRemainingTimeCoroutine(remainingTimeSeconds: Int) {
    for (time in remainingTimeSeconds downTo 0) {
        if (time > 0) {
            println("updateRemainingTime: $time seconds")
            delay(1000)
        } else {
            println("else")
        }
    }
}