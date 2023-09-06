import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    /**
     * Scope içerisinde yer alan bir suspend fonksiyon yapması gereken işi tamamlayana kadar altındaki bütün kod bloğunu
     * askıya alır. Yani executeProcess() fonksiyonu tamamlanıp iterationsCount dönene kadar printLn() çalışmayacaktır.
     * */
    coroutineScope.launch {
        val iterationsCount = executeProcess()
        println("UI - UI - UI - UI - UI - UI - UI - UI - UI $iterationsCount")
    }
}

/**
 * Yazdığımız kodlar her zaman güvenli olmalı. Yani bir başkası tarafından kullanıldığı durumda bile kolay kolay bozulmamalı.
 * Burda safe olmayan durum ne?
 * main() fonksiyon üzerinde görebileceğiniz üzere ben bir scope yaratıyorum ve bu scope'a default bir dispathcer veriyorum.
 * Yani ben executeProcess() fonksiyonunun Main Dispatcher'da çalışacağını varsayıyorum. Çalışmadığı durumda UI patlayacak.
 * Bu sebeple UI ile ilgili yapacağım işlemi executeProcess() fonksiyonu içerisinden alıp scope altına taşımam gerekiyor.
 * Yukarı taşıdığımda ne olacak artık bu fonksiyonun bana bir değer dönmesi gerekiyorki UI işlemi içerisinde kullanabileyim.
 * Suspend'de olsa fonksiyon fonksiyondur yani değer dönderebilir. 33.dersteki notlardan da hatırlayacağımız gibi withContext
 * ile değer dönderebiliyorum.
 * */
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