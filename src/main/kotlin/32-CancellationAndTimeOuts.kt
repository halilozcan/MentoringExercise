import kotlinx.coroutines.*

fun main() = runBlocking {
    // cancellable()

    // notCancellableComputation()

    // notCancellableWithException()

    // cancellableComputation()

    // cancellableWithFinally()

    // nonCancellableBlockExecution()

    // timeOut()

    timeOutOrNull()
}


suspend fun cancellable() = coroutineScope {
    val a = launch {
        repeat(10) {
            println("job: I'm sleeping ${it}...")
            delay(500L)
        }
    }

    delay(1300)
    println("I'm tired of waiting!")
    a.cancel()
    a.join()
    println("main: Now I can quit")
}

/**
 * Coroutine her zaman cancel edilebilir olmalıdır. kotlinx.coroutines içerisindeki bütün fonksiyonlar
 * cancel edilebilirlerdir. Coroutine ın cancel durumunu kontrol ederler ve cancel olma durumunda CancellationException
 * fırlatırlar. Eğer bir coroutine bir hesapla işlemiyle çalışıyorsa ve cancellation kontrolü yoksa cancel edilemez.
 */
suspend fun notCancellableComputation() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val computationJob = launch {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleep ${i++}")
                nextPrintTime += 500L
            }
        }
    }

    delay(300L)
    println("main: I'm tired of waiting!")
    computationJob.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * Buradaki kod parçacığı çalışmasına devam eder. Ancak her adımda cancellation exception fırlatıldığı
 * için catch içerisine de kod parçacığı girer
 */
suspend fun notCancellableWithException() = coroutineScope {
    val job = launch(Dispatchers.Default) {
        repeat(5) {
            try {
                println("job: I'm sleeping $it ...")
                delay(500)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Burada computation işleminde isActive kullanılarak coroutine in aktifliğine bakılarak while döngüsünün devam
 * etmesi engellenmiştir.
 */
suspend fun cancellableComputation() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Burada iş cancel olmasına rağmen finally bloğu çalışır
 */
suspend fun cancellableWithFinally() = coroutineScope {
    val job = launch {
        try {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

suspend fun nonCancellableBlockExecution() = coroutineScope {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            /**
             * Burada eğer NonCancellable kullanılmazsa suspend görülen yer kill edilir ve altı çalışmaz
             */
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

/**
 * Burada eğer verilen süre içerisinde iş tamamlanmaz ise Exception fırlatılır
 */
suspend fun timeOut() = coroutineScope {
    withTimeout(1300L) {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
    }
}

/**
 * Burada eğer iş parçacığı istenilen süre içerisinde tamamlanmaz ise fonksiyon null döndürür. Tamamlanır ise
 * en son ifadede ne değer varsa o döndürülür
 */
suspend fun timeOutOrNull() = coroutineScope {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
        "Done"
    }

    println("Result is $result")
}