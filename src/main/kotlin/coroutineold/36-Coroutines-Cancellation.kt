package coroutineold

import kotlinx.coroutines.*

fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    /**
     * Biz scope'ları oluştururken genelde içerisinde bir akış olması durumlarında kullanıyoruz. Yani bu scope içerisinde
     * bir thread yaratıyoruz ve diyoruz ki sen çalışacaksın ama ne kadar çalışacağını bilmiyoruz. Bitince bana gelen datayı ver.
     *
     * Bazen öyle durumlar yaşanıyorki bu bekleme durumlarını cancel etmemiz gerekiyor. Örneğin bir sayfada bir işe başladık ve
     * kullanıcı uygulamayı arkaya attı veya sayfa değişti diyelim. Bu durumda o akışın devam etmemesi gerekiyor.
     *
     * Launch fonksiyonunu incelediğimiz de biz kullansakta kullanmasakta arka tarafta bir job yaratıyor. Bu Job'ı kullanarak
     * mevcut akışı cancel edebiliriz.
     * */
    var job: Job?

    job = coroutineScope.launch {
        val iterationsCount = executeProcess()
        println("UI - UI - UI - UI - UI - UI - UI - UI - UI $iterationsCount")
    }

    val dummyBool = true
    /**
     * Dummy bir condition kullanarak Job'ın burada cancel edileceğini söyleyebiliriz. Bu noktada Coroutine ile alakalı
     * önemli bir detayı bilmemiz gerekiyor.
     * !!---- Coroutine halihazırda çalışan bir işlemi öldürmez ----!!
     * Yani bu ne demek 19. satırdaki suspend block çalıştığında job cancel edilirse iterationsCount değeri elde edilir ve
     * altındaki kod satırları cancel edilir.
     * */
    if (dummyBool) {
        job.cancel()
    }

    /**
     * Ayrıca yukarıda gördüğümüz bu jobların sayısı artabilir. Hepsini tek tek cancel etmek yerine kullanabileceğimiz
     * güzel bir yol var. Ben Context'e bağlı tüm jobları "cancelChildren" diyerek context üzerinden aynanda cancel edebiliyorum.
     * */
    var job1: Job? = null
    var job2: Job? = null

    job1 = coroutineScope.launch { delay(1000) }
    job2 = coroutineScope.launch { delay(1000) }
    if (!dummyBool) {
        coroutineScope.coroutineContext.cancelChildren()
    }

    /**
     * cancel kullanılırken dikkat edilmesi gereken bir nokta var. O da context'i değilde coroutine scope'u cancel etmek.
     * Bu işlemin yapılması komple tüm scope'u cancel eder. Yani cancel etme işlemi başarılı olur fakat bidaha cancel edilen
     * scope'a ait hiçbir launch çalışmaz.
     * */
    coroutineScope.cancel()
}
private suspend fun executeProcess(): Long {

    val benchmarkDurationSeconds = 5
    return withContext(Dispatchers.Default) {
        println("started")
        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds + 1_000_000_000L
        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }
        println("stopped")
        iterationsCount
    }

}