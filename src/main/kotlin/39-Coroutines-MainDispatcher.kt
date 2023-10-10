import kotlinx.coroutines.*

fun mainDispatcher() {

    val mainDispatcher = Dispatchers.Main

    /** Android App'ler içerisindeki muhtemelen en önemli dispatcher.
     * Dispatcher.Main yazılan kodların, Android uygulamaların UI Thread'inde çalıştırılmasını sağlar.
     * */

    val mainImmediateDispatcher = Dispatchers.Main.immediate

    /**
     * Bu noktada yukarıda da görebileceğiniz gibi Main gibi ama tanımı biraz farklı bir dispatcher'ımız daha var.
     * */

    /** ----------------------------- Main vs Main.immediate ----------------------------- */

    /**
     * En başta dediğim gibi Main Dispathcer Android uygulamalarda kullandığımız bir dispatcher.
     * Bu sebeple aradaki farkı anlamak için bir miktar Android bilgisi gerekiyor.
     * */

    /**
     * Dispathcer.Main -> Handler(Looper.getMainLooper()).post() gibi davranır.
     * Dispatcher.Main.immediate -> Activity.runOnUiThread() gibi davranır.
     * */

    /**
     * onStop() fonksiyonu Android Lifecycle'ına ait bir method. Bu fonksiyon çalıştığında log'larda göreceğimiz durum:
     *      1- onStop execute edilir.
     *      2- first
     *      3- second
     *      4- third
     * Şeklinde bir çıktı görürüz. Beklenen de budur zaten.
     * */

    /**
     * Peki onPause() fonksiyonu çalışırsa ne görürüz?
     * */

    /**
     * onPause() fonksiyonu da Android Lifecycle'ına ait bir method. Bu fonksiyon çalıştığında log'larda göreceğimiz durum:
     *      1- onPause execute edilir. second
     *      2- first
     *      3- third
     * Göreceğimiz çıktı yukarıdaki gibi olur. immediate kullandığımız da içinde bulunduğumuz fonksiyon execute edilirken
     * aynı zamanda yapılacak ilk iş immediate olarak belirtilen dispatcer'ın da çalıştırılması olacaktır.
     * */

    /**
     * immediate kullanımı daha performanslıdır.
     * Not: Android Kütüphanelerinin altında yatan dispatcher çoğunlukla immediate'dır.
     * */

}

fun onStop() {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    coroutineScope.launch(Dispatchers.Main) {
        println("first")
    }
    coroutineScope.launch(Dispatchers.Main) {
        println("second")
    }
    coroutineScope.launch(Dispatchers.Main) {
        println("third")
    }
}

fun onPause() {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    coroutineScope.launch(Dispatchers.Main) {
        println("first")
    }
    coroutineScope.launch(Dispatchers.Main.immediate) {
        println("second")
    }
    coroutineScope.launch(Dispatchers.Main) {
        println("third")
    }
}
