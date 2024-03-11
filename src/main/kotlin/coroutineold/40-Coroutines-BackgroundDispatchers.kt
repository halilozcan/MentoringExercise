package coroutineold

import kotlinx.coroutines.*

/** İki tane Background Dispatcher'ımız var.
 *      1- Dispathcer.Default
 *      2- Dispathcer.IO
 * */

fun main() {
    val default = Dispatchers.Default
    val io = Dispatchers.IO
}

/** ----------------------------- Dispatcher.Default ----------------------------- */

/**
 * Default Dispatcher Thread Pool üzerinde 2 ile sahip olunan çekirdek sayısı arasından maksimumu seçer.
 * Resim işleme gibi çok fazla ve yoğun hesap yapılan işlemler için kullanılması gereken dispatcherdır.
 * */

/** ----------------------------- Dispatcher.IO ----------------------------- */

/**
 * IO -> Input-Output
 * IO Dispathcer'da ise Thread Pool üzerinde 64 ile çekirdeğin sahip olduğu max core arasından max'ı seçer.
 * Adından da anlaşılacağı üzere input output işlemlerinde kullanılır. Genelleyecek olursak beklememiz gereken işler için diyebiliriz.
 * Yani internetten veri çekeceğiz diyelim isteği attık boş boş verinin gelmesini bekliyoruz. Kullanmamız gereken dishpacher, IO.
 * */

/**
 * Muhtemel soruları şöyle cevaplayalım:
 *
 * Soru 1: Internet işleri için Default kullanamaz mıyım?
 * Cevap 1: Kullanabiliriz ama bu performans azalışı olarak geri dönebilir. IO direkt 64 thread'i görevlendirirken,
 * Default bunu 2 ile de yapıyor olabilir.
 *
 * Soru 2: 64 tane thread'i verdik network işlerini halletsinler diye. Peki bu kadar thread'i blocklamaya değer mi?
 * Cevap 2: Block kelimesini duyduğumuz zaman coroutine içerisinde iki kere düşünmemiz gerekiyor. Blocklama Thread'de olur dedik
 * buradaki durum suspending. Yani bekleyebilirler bu bir sorun değil.
 *
 * Soru 3: 2 tane az 64 tane de fazla. Başka da dispatcher yok. Ne yapabiliriz?
 * Cevap 3: Kendi Dispatcher'larımızı yazabiliyoruz.
 * */



/**
 * !!! Dispatcher.Unconfined anlatılmayacaktır.
 * */