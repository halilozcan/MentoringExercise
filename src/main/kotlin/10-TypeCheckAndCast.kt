/**
val x: String = ""

if (x is String) {
// Smart Cast
x.length
}

if (x !is String) {
println(x)
}

if(x !is String) return
// Smart Cast obj.length


// x is automatically cast to String on the right-hand side of `||`
if (x is String || x.length == 0) return

// x is automatically cast to String on the right-hand side of `&&`
if (x is String && x.length > 0) {
print(x.length) // x is automatically cast to String
}

// Smart Cast
when (x) {
is Int -> print(x + 1)
is String -> print(x.length + 1)
is IntArray -> print(x.sum())
}

// SmartCast Koşulları
val lokal değişkenler - her zaman smart cast olabilirler (delegated properties haricinde)
val propertyler -> property private sa ya da internalsa veya check işlemi aynı module içerisinde ypaılıyorsa veya custom
getter yoksa smart cast yapılabilir
var lokal değişkenler -> kullanım ve kontrol aşamasında değiştirilme yoksa veya kendisini değiştiren lambda yoksa ve local
delegated property değilse smart cast yapılabilir
var property -> asla yapılmaz çünkü her zaman değiştirilebilirler.
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

fun main() {

}