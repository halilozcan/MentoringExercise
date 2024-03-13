import kotlinx.coroutines.*

/**
 * Coroutine context in temel elemanları coroutine in Job ı ve dispatcher ıdır.
 */

/**
 * Coroutine context i coroutines in hangi thread veya threadlerde çalışacağına karar veren coroutine dispatcher ı
 * içerir. Coroutine dispatcher ı coroutine in çalışmasına spesifik bir thread atayabilir, thread pool a yayabilir
 * ya da bir sınır olmadan çalışmasına karar verir.
 */

fun main() = runBlocking {
    // simpleThread()

    // unconfinedDispatcher()

    // contextSwitch()

    // jobInContext()

    // childrenCoroutine()

    // parentResponsibility()

    // namingCoroutine()

    // combineContextElements()

    // coroutineScope()
}

suspend fun simpleThread() = coroutineScope {
    /**
     * launch parametresiz olarak kullanılırsa, nereden launch edildiyse oranın context ini(yani dispatcher ını)
     * kalıtım alır. Aşağıdaki örnekte, main thread üzerinde çalışan main runBlocking coroutine context ini inherit eder.
     */
    launch {
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }

    /**
     * Dispatcher Unconfined da main thread üzerinde farklı bir mekanizmayla çalışır.
     */
    launch(Dispatchers.Unconfined) {
        println("unconfined: I'm working in thread ${Thread.currentThread().name}")
    }

    /**
     * Default dispatcher scope içerisinde herhangi bir dispatcher belirtilmediğinde kullanılır. Shared Background
     * thread pool dan bir thread alır
     */
    launch(Dispatchers.Default) {
        println("default : I'm working in thread ${Thread.currentThread().name}")
    }

    /**
     * newSingleThreadContext coroutine in çalışması için bir thread üretir. Atanan bu thread expensive bir resource dur.
     * Gerçek uygulamada bu thread ihtiyaç olduğunda close fonksiyonuyla release edilmelidir ya da uygulama boyunca
     * top-level variable olarak tutulmalıdır
     */
    launch(newSingleThreadContext("MyOwnThread")) {
        println("newSingleThreadContext : I'm working in thread ${Thread.currentThread().name}")
    }
}

suspend fun unconfinedDispatcher() = coroutineScope {
    /**
     * Unconfined dispatcher ilk suspension point e kadar kendisi çağıran thread üzerinde coroutine başlatır.
     * Suspension dan sonra suspend fonksiyonun başlatıldığı thread üzerinde coroutine i devam ettirir. Unconfined
     * Dispatcher CPU time ı tüketen veya belirli bir thread e sınırlı kalmayan herhangi bir paylaşımlı datayı
     * update eden (örn. UI) coroutine ler için uygundur.
     */
    launch(Dispatchers.Unconfined) {
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
    }

    launch {
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }
}

fun contextSwitch() {
    /**
     * Context switching için birden fazla teknik vardır. Bunlardan bir tanesi runBlocking ile açık olarak context
     * belirtmektir. Diğeri ise aynı coroutine içinde çalışırken withContext() fonksiyonu kullanılmasıdır
     */
    newSingleThreadContext("Context1").use { context1 ->
        newSingleThreadContext("Context2").use { context2 ->
            runBlocking(context1) {
                println("[${Thread.currentThread().name}] Started in Context1")
                withContext(context2) {
                    println("[${Thread.currentThread().name}] Working in Context2")
                }
                println("[${Thread.currentThread().name}] Back to Context1")
            }
        }
    }
}

suspend fun jobInContext() = coroutineScope {
    println("My job is ${coroutineContext[Job]}")
    launch(SupervisorJob() + Dispatchers.IO) {
        println("My job is ${coroutineContext[Job]}")
    }
}

suspend fun childrenCoroutine() = coroutineScope {
    val request = launch {
        launch(Job()) {
            println("job1: I run in my own Job and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }

        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }

    delay(500)
    request.cancel()
    println("main: Who has survived request cancellation?")
    delay(1000)
}

suspend fun parentResponsibility() = coroutineScope {
    /**
     * Parent coroutine her zaman bütün children ların bitmesini bekler. Bir parent başlattığı childrenlarını
     * açık olarak takip etmek zorunda değildir ve onların bitmesi için job.join ı çağırmak zorunda değildir.
     */
    val request = launch {
        repeat(3) {
            launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }

    request.join()
    println("Now processing of the request is complete")
}

suspend fun namingCoroutine() = coroutineScope {
    println("[${Thread.currentThread().name}] Started in main coroutine")

    val v1 = async(CoroutineName("v1Coroutine")) {
        delay(500)
        println("[${Thread.currentThread().name}] Computing v1")
        252
    }

    val v2 = async(CoroutineName("v2Coroutine")) {
        delay(500)
        println("[${Thread.currentThread().name}] Computing v2")
        6
    }

    println("[${Thread.currentThread().name}] The answer for v1 / v2 = ${v1.await() / v2.await()}")
}

suspend fun combineContextElements() = coroutineScope {
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
}

suspend fun coroutineScope() {
    val activity = Activity()
    activity.doSomething()
    println("Launched coroutines")
    delay(500)
    activity.destroy()
    delay(1000)
}

class Activity {
    private val mainScope = CoroutineScope(Dispatchers.Default)

    fun destroy() {
        mainScope.cancel()
    }

    fun doSomething() {
        repeat(10) {
            mainScope.launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
    }
}