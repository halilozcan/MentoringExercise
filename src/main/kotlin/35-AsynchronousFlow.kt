import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/**
 * Bazen internet(network), yerel veritabanı vb. yerlerden veri çekerken veri belirli veya belirsiz zaman aralıklarıyla
 * gelir. Bu tür turumlarda bir emitter (yayıcı) ve bir collector(toplayıcı) vardır. Emitter taraf veriyi yayarken
 * collector bu verileri toplar. Bu tür durumlarda kotlin de flow kullanırız.
 */

fun main() = runBlocking {
    // simple().forEach { println(it) }

    // simpleSequence().forEach { println(it) }

    // simpleSuspend().forEach { println(it) }

    // simpleFlowCollectionSample()

    // coldFlowSample()

    // flowCancellation()

    // simpleConvertFlow()

    // mapFlow()

    // transformFlow()

    // sizeLimitingFlow()

    // terminalFlowOperator()

    // sequentialFlow()

    // flowContext()

    // commonPitfallWhenUsingContext()

    // flowOnOperator()

    // collectWithBuffer()

    // conflateFlow()

    // collectLatest()

    // zipFlow()

    // combineFlow()

    // mapFlow(10)

    // flatMapConcat()

    // flatMapMerge()

    flatMapLatest()
}

// Listeyi tek seferde çekme işlemi
fun simple() = listOf(1, 2, 3)

fun simpleSequence(): Sequence<Int> = sequence {
    for (i in 1..3) {
        // Burası main thread i bloklar
        Thread.sleep(100) // değerinin hesaplanmasının simülasyonu
        yield(i) // bir sonraki veriyi yield eder
    }
}

/**
 * Burada List<Int> şeklinde dönüş değeri vermek sadece tek seferde bu listenin döndürülmesi demektir.
 */
suspend fun simpleSuspend(): List<Int> {
    delay(1000) // asenkron işlem yapılmasının simülasyonu
    return listOf(1, 2, 3)
}

suspend fun simpleFlowCollectionSample() = coroutineScope {
    /**
     * Buradaki kod main threadi bloklamaksızın her sayıyı yazdırmadan önce 1 saniye bekler.
     */
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(1000)
        }
    }

    simpleFlow().collect {
        println(it)
    }
}

fun simpleFlow() = flow {
    for (i in 1..3) {
        delay(1000)
        // bu ifade main thread i bloklar
        //Thread.sleep(1000)
        emit(i)
    }
}

suspend fun coldFlowSample() = coroutineScope {
    /**
     * Burada simpleColdFlow() un suspend olarak işaretlenmemesinin sebebi cold flow olmasıdır. simpleColdFlow()
     * fonksiyonu hızlıca dönüş yapar ve herhangi bir şeyi beklemez. Bundan dolayı flow her collect edildiğinde
     * Flow started ın çalıştığı görülür.
     */
    println("Calling simple function...")
    val flow = simpleColdFlow()
    flow.collect {
        println(it)
    }
    println("Calling collect again")
    flow.collect {
        println(it)
    }
}

/**
 * Flowlar cold stream lerdir. flow builder ın içerisindeki kod flow collect edene kadar çalışmaz.
 */
fun simpleColdFlow() = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(100)
        println("Emitting $i")
        emit(i)
    }
}

suspend fun flowCancellation() {
    /**
     * Flow, coroutine lerin cancel olmasına bağlı kalırlar. Genelde, flow ın collect edilmesi delay vb. cancellable
     * suspend fonksiyonlarla cancel edilebilir
     *
     */
    withTimeoutOrNull(250) {
        simpleColdFlow().collect {
            println(it)
        }
    }
    println("Done")
}

/**
 * range, liste gibi yapılar flowlara çevrilebilir
 */
suspend fun simpleConvertFlow() {
    (1..3).asFlow().collect {
        println(it)
    }
}

/**
 * Flowlar aynı collectionlar ve  da olduğu gibi bazı operatörler kullanılarak dönüştürülebilir. Ara operatörler
 * upstream(yukarı akan) flowlara uygulanır ve downstream(aşağı akan) flow döndürür. Bu operatörler cold dur. Bu
 * operatörler suspend değildirler ancak bu operatörlerin içinde suspend fonksiyon çağırılabilir. Hızlıca çalışırlar
 * ve transform sonucunda yeni bir flow döndürürler.
 */

suspend fun mapFlow() {
    (1..3).asFlow().map {
        performRequest(it)
    }.collect {
        println(it)
    }
}

/**
 * Genelde en çok kullanılan operatörlerden birisi transformdur. Transform operatörüyle beraber ara değerlerin
 * emit olması sağlanabilir.
 */
suspend fun transformFlow() {
    (1..3).asFlow().transform {
        emit("Making Request $it")
        emit(performRequest(it))
    }.collect {
        println(it)
    }
}

suspend fun performRequest(request: Int): String {
    delay(1000)
    return "response:$request"
}

/**
 * Take vb. terminal operatörler limite erişildiğinde flow u cancel eder. Coroutines lerde cancellation her zaman
 * exception fırlatılarak yapılır. Bundan dolayı kaynak yönetimi vb. işlemler try - catch - finally arasında
 * yapılmalıdır.
 */
suspend fun sizeLimitingFlow() {
    numbersFlow().take(2).collect {
        println(it)
    }
}

suspend fun numbersFlow() = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } catch (e: Exception) {
        println("Error:${e.message}")
    } finally {
        println("Finally in numbersFlow")
    }
}

/**
 * Terminal operatörler flow un collect edilmesini başlatan suspend fonksiyonlardır. collect() fonksiyonu en basit
 * örnek olarak verilebilir. Bunun gibi fazlasıyla terminal operatör vardır. Bazıları;
 * toList ve toSet gibi çevirim operatörleri
 * first()
 * reduce ve fold
 */
suspend fun terminalFlowOperator() {
    val sum = (1..5).asFlow().map { it * it }.reduce { accumulator, value -> accumulator + value }
    println(sum)
}

/**
 * Birden fazla flow üzerinde çalışan operatörler kullanılmadığı sürece flow un emit ettiği değerler sırasıyla
 * collect edilir. Collect işlemi terminal operatörü çağıran coroutine içerisinde direkt olarak yapılır. Yeni
 * bir coroutine launch edilmez. Emit edilen her bir değer upstream den downstreame kadar her bir ara operatör
 * tarafından işlenir ve terminal operatöre iletilir.
 */
suspend fun sequentialFlow() {
    (1..5).asFlow().filter {
        println("Filter $it")
        it % 2 == 0
    }.map {
        println("Map $it")
        "string $it"
    }.collect {
        println("Collect $it")
    }
}

/**
 * Flow un collect edilmesi her zaman çağırılan coroutine context inde gerçekleşir. Bu context preservation
 * olarak adlandırılır. Varsayılan olarak flow { } içerisindeki kod collector tarafından sunulan context içerisinde
 * çalışır
 */
suspend fun flowContext() {
    simpleFlow().collect {
        println("[${Thread.currentThread().name}] Collected $it")
    }
}

suspend fun commonPitfallWhenUsingContext() {
    contextChangeFlow().collect {
        println(it)
    }
}

suspend fun contextChangeFlow() = flow {
    /**
     * Uzun süren CPU tüketen kodlar Dispatcher.Default içerisinde çalışabilir ve UI update işlemi Dispatcher.Main
     * context inde çalıştırılabilir. Genel olarak withContext coroutines lerde context i değiştirmek için kullanılır
     * ancak flow {} builder ı çağırılan context üzerinde emit etmelidir ve başka context içerisinde emit e izin
     * verilmez.
     */
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            Thread.sleep(1000)
            emit(i)
        }
    }
}


suspend fun flowOnOperator() {
    simpleSleep().collect {
        println(it)
    }
}

/**
 * flowOn operatörü flow un emit edildiği yerin context ini değiştirmek için kullanılır. Aşadağı emit işlemi
 * flow { } background thread de gerçekleşirken, collection main thread de gerçekleşir.
 *
 * Burada flowOn operatörü default sequential behavior ı değiştirir. Collection bir coroutine de gerçekleştirilirken
 * emission ise collecting ile eş zamanlı çalışan bir coroutine de gerçekleşir. flowOn operatörü kendi contextindeki
 * CoroutineDispatcher ı değiştirmesi gerektiği zaman upstream flow için başka bir coroutine yaratır.
 */
fun simpleSleep() = flow {
    for (i in 1..3) {
        Thread.sleep(1000)
        println("[${Thread.currentThread().name}] Emitting $i")
        emit(i)
    }
}.flowOn(Dispatchers.Default)

suspend fun collectWithBuffer() = coroutineScope {
    /**
     * Buradaki kod parçacığı 100 ms emit için bekler, 300 ms de collection için bekler. Böylece her bir item
     * yazdırıldığın 400ms geçmiş olur
     */
    val time = measureTimeMillis {
        simpleDelay().collect {
            delay(2000)
            println(it)
        }
    }
    println("Collected in $time ms")

    /**
     * buffer operatörüyle sequential olarak alınması yerine eş zamanlı olarak çalışma sağlanır. Aşağıdaki
     * kod parçacığın ilk numaranın collect edilmesi için 100ms beklenir ardından her bir item için sadece
     * 300 ms beklenmiş olur.
     *
     * flowOn operatörü de context i değiştirmek zorunda kaldığı zaman aynı buffer mekanizmasını kullanır ancak
     * burada görüldüğü üzere herhangi açık olara herhangi bir context değişimi bulunmamaktadır.
     */
    val bufferTime = measureTimeMillis {
        simpleDelay().buffer().collect {
            delay(2000)
            println(it)
        }
    }

    println("Collected with buffer in $bufferTime ms")
}

fun simpleDelay() = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

/**
 * Flow un gönderdiği değerlerin hepsinin işlenmesi gerekmediği ve en güncel değerlerin alınabilmesinin yeterli
 * olduğu durumlar olabilir. Bu durumlarda Conflate collector itemları collect etmede yavaş kalındığı zaman
 * ara değerleri atlamak için kullanılır.
 */
suspend fun conflateFlow() = coroutineScope {
    val time = measureTimeMillis {
        simpleDelay().conflate().collect {
            delay(300)
            println(it)
        }
    }
    println("Collected in $time ms")
}

/**
 * Conflation emitter ve collector ın yavaş olduğu durumlarda işlemeyi hızlandırmak için kullanılan yollardan
 * bir tanesidir. Diğer bir seçenek için yavaş olan collector ı cancel edip her bir veri emit edildiğinde
 * tekrardan başlatmaktır.
 */
suspend fun collectLatest() = coroutineScope {
    val time = measureTimeMillis {
        /**
         * Collect latest 300 ms ve emit edilmesi 100ms sürdüğü için collecting her seferinde yazdırılır ancak
         * done son elemanda yazdırılır.
         */
        simpleDelay().collectLatest {
            println("Collecting value $it")
            delay(300)
            println("Done $it")
        }
    }
    println("Collected in $time ms")
}

/**
 * zip karşılıklı gelen itemlarının birlikte emit edilmesini sağlar
 */
suspend fun zipFlow() = coroutineScope {
    val numbers = (1..3).asFlow()
    val strings = flowOf("one", "two", "three")

    numbers.zip(strings) { number, string ->
        "$number $string"
    }.collect {
        println(it)
    }
}

/**
 * combine edilen flowların birisi emitleme yaptığı zaman collector tarafında diğerinin
 * en son emit edilen elemanı ve güncel olarak emitlenen değer gelir.
 *
 * Örn;
 * 1 one
 * 2 one
 * 2 two
 * 3 two
 * 3 three
 */
suspend fun combineFlow() = coroutineScope {
    val numbers = (1..3).asFlow().onEach { delay(300) }
    val strings = flowOf("one", "two", "three").onEach { delay(400) }
    val startTime = System.currentTimeMillis()

    numbers.combine(strings) { number, string ->
        "$number $string"
    }.collect {
        println("$it at ${System.currentTimeMillis() - startTime} ms from start")
    }
}

/**
 * Flow asenkron ve sıralı bir şekilde değer alırken aynı zaman da bu alınan değerler için de bir istek atılabilir.
 * Bu isteğin sonucu da bize bir flow döndürülebilir. Aşağıdaki gibi bir durumda Flow<Flow<String>>> gibi bir flow
 * yapısı oluşur. Bu flow yapısını tek parça haline getirmek gereklidir.
 */
suspend fun mapFlow(a: Int) = coroutineScope {
    (1..3).asFlow().map {
        requestFlow(it)
    }.collect {
        println(it)
    }
}

fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500)
    emit("$i Second")
}

/**
 * flatMapConcat ile beraber içerideki flow un bitmesi beklenir ve içerideki flow bittikten sonra yeni
 * değere geçilir
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun flatMapConcat() = coroutineScope {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .onEach { delay(100) }
        .flatMapConcat {
            requestFlow(it)
        }.collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
}

/**
 * flatMapMerge eş zamanlı olarak gelen flowları collect eder ve değerlerini tek bir flowa birleştirir. Böylece
 * mümkün olduğunca değerler kısa bir süre içerisinde emit edilmiş olur.
 */
suspend fun flatMapMerge() = coroutineScope {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .onEach { delay(100) }
        .flatMapMerge { requestFlow(it) }
        .collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }

    // Buradaki kod parçacığı yukarısı ile aynı işlemi yapar
    /*(1..3).asFlow()
        .onEach { delay(100) }
        .map {
            requestFlow(it)
        }.flattenMerge()
        .collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }*/
}

/**
 * flatMapLatest ile upstream flowdan her bir değer emit edildiğinde içerideki flow cancel eder.
 */
suspend fun flatMapLatest() = coroutineScope {
    val startTime = System.currentTimeMillis()
    (1..3).asFlow()
        .onEach { delay(100) }
        .flatMapLatest {
            requestFlow(it)
        }.collect {
            println("$it at ${System.currentTimeMillis() - startTime} ms from start")
        }
}




