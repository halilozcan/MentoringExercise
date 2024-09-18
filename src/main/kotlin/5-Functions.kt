fun main() {
    sum(2, 3)

    sumDefault(4)

    sumDefault(a = 2, b = 3)

    add("1", "2", "3", *arrayOf("2", "5", "6"), "3")
}

/**
 * fun anahtar kelimesi ile tanımlanır
 * parametreler pascal gösterimi ile yazılır - name:type ve virgül ile ayrılır
 * her bir parametrenin tipi açık olarak belirtilmelidir.
 * son parametreye de noktalı virgül konulabilir.
 *
 */
fun sum(a: Int, b: Int): Int {
    return a + b
}

/**
 * default argumentler overload işlemini azaltır. sırasıyla istediğiniz elementi verebilirsiniz
 * birden fazla kullanım olayını göster. tam tersi argüment sıralamasını da göster. birden fazla
 * default argumentli örnek göster.
 * Java fonksiyonu çağırıldığı zaman named argument syntax ı kullanılamaz.
 */
fun sumDefault(a: Int, b: Int = 0): Int {
    return a + b
}

/**
 * Eğer bir fonksiyon bir şey döndürmüyorsa onun dönüş tipi Unit olur. Unit için de ekstradan dönüş tipi
 * belirtmeye gerek yoktur. Sadece bir parametre vararg olarak işaretlenebilir. eğer vararg son eleman
 * değilse diğer parametreler named argument olarak kullanılabilir.
 */
fun add(vararg sentences: String): Unit {

}

/**
 * Fonksiyon tek bir ifadeden oluşacaksa o zaman return ve süslü parantez kullanmaya gerek duyulmaz
 * ve sadece ifade yazılır. İstenirse dönüş tipi belirtilebilir. Eğer fonksiyon body şeklinde yazılırsa
 * kesinlikle return type Unit olmadığı sürece belirtilmelidir. Fonksiyonlarda type inference yoktur.
 */
fun sumSingle(a: Int, b: Int): Int = a + b

/**
 * Bazen sınıf içerisindeki bazı methodlar birbiriyle alakasız olabilirler. Bundan dolayı birbiriyle alakalı işlemleri
 * bir arada tanımlayabilmek için fonksiyon içerisinde fonksiyon tanımlanması yapılabilir. Local fonksiyonlar
 * kendileri çağırılmadan önce tanımlanmalıdır. Ayrıca local fonksiyonlar bir üst methodun değişkenlerine erişebilir.
 * Bu değişkenler referans tipli değişkenlere dönüşür ve alt method içerisinden erişilebilir.
 */

fun login(userName: String, password: String): Boolean {
    fun validateInput(input: String) {
        if (input.isEmpty()) {
            throw Exception("Input can not be empty")
        }
    }

    validateInput(userName)
    validateInput(password)
    return true
}