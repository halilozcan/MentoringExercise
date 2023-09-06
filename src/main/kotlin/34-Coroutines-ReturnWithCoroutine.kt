import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    coroutineScope.launch {
        executeProcess()
    }
}

/**
 * Ben halihazırda scope yaratırken default bir Dispathcer veriyorum. Bu sebeple dispatcher değiştirmediğim durumlarda
 * default verdiğim dispatcher çalışacak. O zaman 32. Dersteki notlara bakarak Main Dispatcher kullandığım withContext'in
 * anlamsız olduğunu söyleyebiliriz. withContext'i kaldırıp UI işlemini Default Dispatcher'ın altına aldığımızda artık
 * scope farkından dolayı içerdeki değişkene ulaşamıyorum.
 *
 * Adım adım görelim diye fonksiyonu klonladım. executeProcess2()'den devam edelim.
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
        println("stopped")
    }
    //                          Hata vermesin diye başındaki dolar işaretini kaldırdım.
    println("UI - UI - UI - UI - UI - UI - UI - UI - UI iterationsCount")

}

/**
 * withContext olarak kullandığım bu suspending fonksiyon dökümanı incelediğimde görüyorumki bana bir şey dönüyor.
 * Bu Thread ve Coroutine arasındanki en önemli fark. Thread kullandığımız senaryo da böyle bir şey yapamayız.
 * */
private suspend fun executeProcess2() {

    val benchmarkDurationSeconds = 5
    val iterationsCount = withContext(Dispatchers.Default) {
        println("started")
        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds + 1_000_000_000L
        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }
        println("stopped")
        iterationsCount
    }
    println("UI - UI - UI - UI - UI - UI - UI - UI - UI $iterationsCount")

}