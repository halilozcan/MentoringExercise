/**
 * Null bir property nin değerinin kesin var olmayacağını bildiğimiz durumlarda
 * kullanılır. Eğer değer gelmediği durumlarda bir property vs yi güvenli bir şekilde
 * kullanmazsak null pointer exception (npe) ile karşılaşılır
 */

fun main() {
    // tipi nullable olmayan bir değişkene null değeri atanamaz
    var a: String = "Hello"
    // a = null

    var b: String? = "Hello I am nullable"
    b = null
    println(b)

    /**
     * nullable olmayan bir property nin üyelerine güvenli bir şekilde erişebiliriz
     * ama nullable olan bir property nin üyelerine güvenli erişmemize izin verilmez
     */

    // val bLength = b.length

    val bLength = if (b != null) b.length else -1

    val result = if (b != null && b.length > 0) {
        b
    } else {
        null
    }

    // safety ?. operatörü kullarak üyelere erişim sağlanabilir.
    println(a?.length) // gereksiz kullanım a null değil
    println(b?.length) // b null değilse uzunluk null ise null yazdırılır.

    /**
     * safety operatörü ile chained call yapılabilir
     */

    // eğer herhangi bir chain null ise sonuç olarak null döndürülür.
    val sum = b?.length?.sumWith(3)


    val person: Person? = null

    /**
     * Chain olarak kullanımda sol tarafta assignment şeklinde kullanım da
     * yapılabilir. Eğer chained ifade null ise assignment yapılmadan geçilir
     * ve sağ taraf hiçbir şekilde değerlendirilmez.
     */
    person?.height = 15

    /**
     * Nullable receiver ile extension yazma
     */
    person.toString()

    /**
     * Eğer nullable yazulan extensionların nullable döndüğü durumun handle
     * edilmesi isteniyorsa ?. operatör kullanımı yapılır.
     */
    val name = person?.name
    if (name != null) {

    }

    /**
     * val bLength = if (b != null) b.length else -1
     * bu ifade yerine daha basit bir şekilde elvis operatör ?: kullanarak
     * if-else kullanımı yapılabilir
     */

    val resultOfElvis = b?.length ?: -1

    /**
     * Eğer bir değer non-null tipe çevirmek istenirse
     * !! (not-null assertion operator) kullanılabilir. Eğer ifade null ise
     * npe fırlatılır. null değilse programın çalışması devam eder.
     */

    /**
     *  // Unsafe Cast
     * // Null nullable olmayan değere çevrilemez
     *  val x:String = y as String
     *  val x:String? = y as String?
     *
     *  // Safe Cast
     *  val x:String? = y as? String
     */
}

fun getPersonInfo(person: Person?): String? {
    val name = person?.name ?: return null
    val gender = person?.gender ?: throw Exception("Person gender can not be null")
    return "$gender $name"
}