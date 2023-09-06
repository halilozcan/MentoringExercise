import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    executeProcess()
}

private fun executeProcess() {
    /** CoroutineScope dediğimiz şeyi Coroutine için bir toolbox olarak düşünebiliriz.
     * Dispatchers dediğimiz kısmı da yarattığımız thread olarak düşünelim. */
    /** Dispatcher kısmına ilerde değineceğiz. Bu kısımda somut örnekler üzerinden ilerleyebilmek adına simüle ediyoruz.
     * Dispatcher.Main Android'de UI işlemlerinden sorumlu bir Thread'dir. Dolayısıyla bu kısımda çalışmayacak. */
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    val benchmarkDurationSeconds = 5

    /**
     * Oluşturduğum scope'da kullandığım launch fonksiyonu bir Coroutine Builder'dır. Yani coroutine oluşturmamızı sağlar.
     * Background'da yapmak istediğim işlemler için oluşturduğum coroutine'i Default Dispatcher'a çekiyorum.
     * */
    coroutineScope.launch(Dispatchers.Default) {
        println("started")
        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds + 1_000_000_000L
        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }
        /**
         * Daha önce de bahsettiğimiz gibi. UI ile ilgili her işlem MAIN Dispatcher'da yapılmak zorunda!!
         * */
        withContext(Dispatchers.Main.immediate) {
            println("UI - UI - UI - UI - UI - UI - UI - UI - UI $iterationsCount")
        }
        println("stopped")
    }

}