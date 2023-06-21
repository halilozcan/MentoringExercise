/**
 *
Integers
// Derleyici otomatik olarak tipi kabul eder.
// Eğer sayı int i aşarsa tip Long olur
// Unsigned integer a bakın

val one = 1
val threeMillion = 3000000
val oneLong = 1L

val pi = 3.14
val oneDouble = 1.0
// val one:Double = 1

val e = 2.7182818284
val eFloat = 2.7182818284f // sadece 6 dizi geçerli olur float f eklenerek gösterilir

// Hexadecimal 0X0F
// Binary 0b0001
// Sayıları daha iyi ifade etme için _ ile ayırabilirsiniz
// val oneMillion = 1_000_000 constant için

// Eğer bir değişkeni nullable yaparsanız örneğin Int? aynı sınıf gibi davranır. Aksi takdirde primitive gibi davranır.
// -128 ile 127 arasında memory optimizasyon yapıldığı için nullable ile normal değerler denktir ama eşit değillerdir.

// Küçük tipler büyük tiplere direkt olarak çevrilemezler. Extension fonksiyonları mevcuttur.
// Farklı tipler birbiri ile karşılaştırılamazlar.

// Aritmetik operasyonlarda çevirmeye gerek olmaz val l = 1L + 3 -> Long
 */

/**
 * Booleans
 * || && !
 */

/**
 * Char
 * val aChar = 'a'
 */

/**
 * Strings
Stringlerin karakterleri bir döngü aracılığıyla gezilebilir
Stringler immutable dır bir kere değer atadınız mı onu değiştiremezsiniz
val str = "abcd"
str.toUpperCase burada str değişmez sonuç yeni bir string olarak ürtilir.

val s = "abc" + 1 burada abc+1 olarak kullanılır

val text = """
    for (c in "foo")
        print(c)
"""
yukarı boşluklar geçerlidir ve alt satıra geçirme olmamasına rağmen text yazılır

val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
""".trimMargin()
burada boşluklar silinir. Sebebi trim margin e default olarak | parametre olarak gider ve yanındakiler silinir.

String template
val i = 10 println("i = $i")
val s = "abc"
println("${s.length}")

// raw String dolar eklemek için de curly braces kullanılır.
val price = """
${'$'}_9.99
"""
 */

/**
Arrays
val asc = arrayOf()
val array = Array(5){
(it*it).toString()
}

val a = arrayOf(1,"",3,null)
 */


fun main() {
    val x:String = ""

    x.isBlank()
}