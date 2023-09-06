/**
 * Bazı durumlarda primitive typeları wrap edip bir sınıfın içerisine gömmek gerekir.
 * Bu durumlarda runtime performance fazlasıyla azalır. Bunun nedeni primitive
 * tiplerin ağır bir şekilde runtime tarafından optimize edilmesidir.
 *
 * Bu durumdan kurtulmamıza sağlayan içerisine nesne konulsa bile primitive tip olarak
 * davranmasını compiler a söyleyen yapı value (inline) classlardır. Inline class lar
 * value anahtar kelimesi ile tanımlanır.
 */

/**
 * JVM de desteklenmesi için başına @JvmInline annotation ı konur.
 */
@JvmInline
value class UserRequest(private val loginId: String) : Logging {
    init {
        require(loginId.isNotEmpty()) {
            throw Exception("login id can not be empty")
        }
    }

    /**
     * Kotlin 1.9.0 ile birlikte inline value sınıflar body e sahip olabilirler.
     */
    constructor(firstName: String, lastName: String) : this("$firstName$lastName") {
        require(firstName.isNotEmpty()) {
            throw Exception("first name can not be empty")
        }

        require(lastName.isNotEmpty()) {
            throw Exception("last name can not be empty")
        }
    }

    override fun logId(): String {
        return loginId
    }
}

interface Logging {
    fun logId(): String
}

/**
 * Inline classların sadece bir property si bulunabilir.
 */

fun asInline(f: UserRequest) {}
fun <T> asGeneric(x: T) {}
fun asInterface(i: Logging) {}
fun asNullable(i: UserRequest?) {}

fun <T> id(x: T): T = x

/**
 * Aşağıdaki fonksiyonların ikisi de JVM tarafında string olarak alacağı gözüktüğü
 * için JVM tarafında fonksiyon public final void logUserId-<hashcode>(String id)
 * olarak gözükür
 */

fun logUserId(id: String) {

}

/**
 * Java tarafından çağırılacağı zaman ismini değiştirmek için JVM name annotation ı
 * kullanılır
 */
@JvmName("logUserRequest")
fun logUserId(id: UserRequest) {

}

fun main() {
    /**
     * Bu ifade runtime da sadece String olarak gösterilir.
     */
    val userRequest = UserRequest("1234#asda")

    asInline(userRequest)    // String
    asGeneric(userRequest)   // userRequest
    asInterface(userRequest) // userRequest
    asNullable(userRequest)  // String?

    /**
     * User Request ilk başta UserRequest olarak gitse de kendisini
     * döndüğü için geriye String olarak döner.
     */
    val c = id(userRequest)
}

