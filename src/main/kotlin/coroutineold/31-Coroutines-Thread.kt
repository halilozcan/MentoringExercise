package coroutineold

fun main() {
    executeProcess()
}

private fun executeProcess() {
    val benchmarkDurationSeconds = 5

    /** Android'de UI Thread olarak adlandırılmayan herhangi bir thread'e background thread denir. */
    /** JVM üzerinde bu şekilde Thread kullanarak background işlemlerini yapabiliyorum. */
    /** Background Thread'ler ile çalışırken dikkat etmemiz gereken nokta USER INTERFACE ile alakalı bir işlem varsa
     * MAIN THREAD kullanılmalı. */
    Thread {
        println("started")

        val stopTimeNano = System.nanoTime() + benchmarkDurationSeconds + 1_000_000_000L
        var iterationsCount: Long = 0
        while (System.nanoTime() < stopTimeNano) {
            iterationsCount++
        }
        println("stopped")
    }.start()
    /** Oluşturduğun Thread'i çalıştırmayı unutma :D */
}