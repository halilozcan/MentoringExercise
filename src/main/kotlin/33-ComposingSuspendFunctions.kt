import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    // operationSequentially()

    // operationAsync()

    // operationAsyncLazily()

    // operationGlobalAsync()

    // operationStructuredConcurrencyAsync()

    operationStructuredFailureAsync()
}

/**
 * Buradaki fonksiyonda sonuçlar alınacağı zaman suspension gerçekleştiği için 2 saniye gibi bir sürenin
 * kod bloğu tamamlanmış olur.
 */
suspend fun operationSequentially() {
    val time = measureTimeMillis {
        val first = doSomethingUsefulOne()
        val second = doSomethingUsefulTwo()
        println("The answer is ${first + second}")
    }
    println("Completed in $time in ms")
}

/**
 * async launch gibidir. Diğer coroutineler ile beraber çalışan ve light-weight bir thread olan coroutine başlatır.
 * launch Job nesnesi döndürür ve dönüş değeri yoktur. Async Deferred(bloklama yapmayan, daha sonra sonuç dönüleceğinin
 * garantisini veren) döndürür. Son değeri almak için deferred üzerinden await çağırılabilir. Deferred aynı zamanda
 * bir Job dır. Bundan dolayı ihtiyaç duyulursa cancel edilebilir
 */
suspend fun operationAsync() = coroutineScope {
    val time = measureTimeMillis {
        val first = async { doSomethingUsefulOne() }
        val second = async { doSomethingUsefulTwo() }
        println("The answer is ${first.await() + second.await()}")
    }
    println("Completed in $time in ms")
}

/**
 * Opsiyonel olarak, async start parametresi CoroutineStart.LAZY verilerek daha sonradan çalışması sağlabilir.
 * Bu modda coroutine in sonucu await edildiğinde ve Job nesnesi üzerinden start çağırıldığında coroutine başlar.
 *
 * Eğer aşağıdaki örnekte start demeden println() fonksiyonu içerisinde sadece await kullanılsaydı bu durumda
 * ilk çağırılan coroutines in çalışmasının bitmesi beklenirdi. Bu da sequential behavior a sebep olurdu.
 *
 * Lazy mode genelde suspend fonksiyonlardan değer döndürülen durumlarda kullanılır.
 */
suspend fun operationAsyncLazily() = coroutineScope {
    val time = measureTimeMillis {
        val first = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val second = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }

        first.start()
        second.start()
        println("The answer is ${first.await() + second.await()}")
    }
    println("Completed in $time in ms")
}

suspend fun operationGlobalAsync() {
    val time = measureTimeMillis {
        // coroutine scope a ihtiyaç duymadan başlatılabilir
        val first = doSomethingUsefulOneAsync()
        val second = doSomethingUsefulTwoAsync()

        /**
         * Sonuçları almak için bir scope içerisinde bulunması gereklidir. Burada runBlock main thread i bloklamak
         * ve sonucu beklemek için kullanılmıştır
         */
        runBlocking {
            println("The answer is ${first.await() + second.await()}")
        }

        /**
         * Burada işlem yapılırken örneğin val first = doSomethingUsefulOneAsync() ve one.await() arasında bir hata
         * olması durumu düşünülürse;
         *
         * Normalde global error handler bu hatayı yakalayabilir, loglayabilir ve developer a raporlayabilir ancak
         * aksi takdirde diğer işlemleri yapmaya devam edebilir. Ancak burada doSomethingUsefulOneAsync() iptal
         * edilmiş olsa bile arkaplanda işlemine devam eder. Bu tarz durumlar structured concurrency ile çözülebilir.
         */
    }
    println("Completed in $time in ms")
}

suspend fun operationStructuredConcurrencyAsync() {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

/**
 * Bu şekilde yaparak, eğer bu fonksiyon içerisinde bir hata olursa ve exception fırlatırsa, buranın içerisindeki
 * tüm coroutine ler cancel edilir.
 */
suspend fun concurrentSum(): Int = coroutineScope {
    val first = async { doSomethingUsefulOne() }
    val second = async { doSomethingUsefulTwo() }
    first.await() + second.await()
}

suspend fun operationStructuredFailureAsync() {
    try {
        failedConcurrentSum()
    } catch (e: Exception) {
        println("Computation failed with Exception:${e.message}")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    /**
     * İptal işlemi her zaman coroutine hiyerarşisi yoluyla yayılır
     * burada two isimli chilren failure olduğu için first async ve kendisinin parent ı cancel edilir.
     */
    val first = async {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("First child was cancelled")
        }

    }

    val second = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException("Something went wrong!")
    }
    first.await() + second.await()
}

fun doSomethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun doSomethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L)
    return 29
}