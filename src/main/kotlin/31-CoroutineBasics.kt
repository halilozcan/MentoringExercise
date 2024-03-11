import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * İnternetten veri çekme, dosyalardan pdf, docx vb işlemler uzun süreli ve uygulamanın çalışmasını bloklayabilecek
 * işlemlerdir. İşletim sistemlerinde bu gibi iş parçacıklarının farklı kollardan yapılıp tek bir hatta birleştirilmesi
 * sağlayan yapılara thread denir. Spesifik olarak belirtilmediği sürece kodlar Main Thread denilen Thread üzerinde
 * çalışır. Kullanıcıyı bloklayabilecek bu iş parçacıkları background threadlerde gerçekleştirilerek Main Thread e
 * sonuçlar aktarılır.
 */

/**
 * Coroutines ler lightweight threadlerdir. Yani coroutine bir thread değildir. Çalışmasına bir thread de başlarken
 * başka bir thread de devam edebilir.
 */

/**
 * runBlocking çağırımında içerisindeki bütün coroutine ler tamamlananana kadar nerede çağırıldıysa orayı bloklar.
 */

fun main() = runBlocking {
    /** launch {

    /**
     * Bir coroutine i suspend(beklemeye almak) etmek üzerinde çalıştığı thread i bloklamaz ve diğer coroutines
     * lerin o threadi kullanmasına izin verir
    */

    /**
     * delay suspend bir fonksiyondur. suspend fonksiyonlar coroutine scope içerisinde çağırıldıkları zaman
     * o iş parçacığını askıya alırlar (iş bitene kadar bekletirler). İş parçaçacığı işini bitirdiğinde program kaldığı yerden çalışmasına
     * devam eder.
    */
    delay(1000L)
    println("World!")
    }
    println("Hello")
     **/

    /**launch {
    /**
     * Suspendable ifade içeren ifadeler bir suspendable fonksiyon içerisine alınabilirler. Böylelikle
     * kod parçacığı daha güzel yapılandırılmış bir şekilde yazılabilir.
    */
    doWorld()

    }
    println("Hello")*/

    // doWorldWithScope()

    /*doCoroutineLaunchesInScope()

    println("Done")*/

    /**
     * corutine launch edildiğinde bir Job nesnesi döndürür. Bu job üzerinden join çağırılarak coroutine in işinin
     * bitmesin için beklenmesi sağlanır ancak diğer coroutine launchları durdurmaz.
     */

    /**val job1 = launch {
    delay(1000)
    println("World Job1")
    }

    val job2 = launch {
    delay(4000)
    println("World Job 2")
    }

    println("Hello")
    job2.join()
    job1.join()
    println("Done")**/

    /**
     * Coroutine ler lightweight threadler olduğu için fazlaca launch edilebilirler ancak aynı şekilde
     * fazlaca thread oluşturulamaz.
     */
    /**repeat(50000){
    launch {
    delay(5000)
    print(".")
    }
    }**/
}

/**
 *
 */
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

/**
 * Bir fonksiyonu suspend yapmakla beraber coroutine scope fonksiyonu kullanılarak scope oluşturulabilir.
 */
suspend fun doWorldWithScope() = coroutineScope {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}

/**
 * Bu şekilde yapılandırılmış bir scope içerisinde hata bulmak daha kolaydır ve dış scope içindeki işler bitmeden
 * önce çalışmasını bitiremez.
 *
 * runBlock güncel threadi bloklarken, coroutine sadece askıya alır ve işi bittikten sonra içinde çalıştığı
 * threadi diğer kullanımlar için serbest bırakır.
 */
suspend fun doCoroutineLaunchesInScope() = coroutineScope {
    launch {
        delay(2000L)
        println("World2")
    }

    launch {
        delay(1000L)
        println("World1")
    }
    println("Hello")
}