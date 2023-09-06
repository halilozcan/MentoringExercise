import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    /**
     * launch içerisine Dispatcher verebildiğimizi görmüştük. Burda vermedim zaman olan şey benim scope oluştururken
     * defaultta verdiğim Dispatcher'ın kullanılması.
     * */
    coroutineScope.launch {
        executeProcess()
    }
}

/**
 * Bizim fonksiyonlar için kullanabildiğimiz birçok terim var. Bu noktada Coroutine ile birlikte aramıza katılan bir yeni
 * keyword'e sahibiz.
 * -------------------------------------------------SUSPEND-------------------------------------------------------------
 * Thread değiştirmek için bir coroutine oluşturmuştum. Ben bu coroutine'i burda oluşturmak istemiyorum ama aynı zamanda
 * fonksiyonun içerisindeki işlemlerde hangi thread'lerin kullanılacağını da seçmek istiyorum. Scope'u fonksiyondan
 * kaldırdığım zaman "withContext" hata vermeye başlayacak. Neden hata veriyor peki:
 * ------ Cevap: Suspend fonksiyonların çalışabilmesi için ya başka bir suspend fonksiyon içinde ya da bir scope altında
 * ------ çağrılmaları gerekir.
 * Suspend keyword'ü ile ilgili bir diğer önemli bilgi ise kodun fonksiyonalitisiyle ilgili hiçbir şey değiştirmez.
 * */
private suspend fun executeProcess() {

    val benchmarkDurationSeconds = 5
    withContext(Dispatchers.Default) {
        println("started")
        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds + 1_000_000_000L
        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }
        withContext(Dispatchers.Main.immediate) {
            println("UI - UI - UI - UI - UI - UI - UI - UI - UI $iterationsCount")
        }
        println("stopped")
    }

}