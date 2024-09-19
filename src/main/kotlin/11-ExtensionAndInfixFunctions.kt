fun main() {
    5 sumWith 3
    5.sumWith(3)
}

/**
 * Bir sınıfa sınıfın kendisini modifiye etmeden başka bir özellik katma extension fonksiyonlar aracılığıyla yapılır.
 * fun Type.funcName(parameters):ReturnType
 * Değerin kendisine this ile ulaşabilir. Property si veya fonksiyonu için this e gerek yoktur
 */

fun Int.findClosestUpperValue(): Int {
    // add() kullanılabilir
    return this + 1
}

fun Int.add(value: String): String {
    return "$this$value"
}

fun Array<Int>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun Int?.nullableExtension(): String {
    return if (this == null) {
        ""
    } else {
        toString()
    }
}

/**
 * Bir fonksiyon infix olarak işaretlenirse . ile çağırılması gerekmez. bu fonksiyonlar member fonksiyon veya
 * extension fonksiyon olmak zorundadır. Sadece tek bir parametre kabul ederler. parametre default value ya sahip
 * olamaz
 */

/**
 * infix fonksiyon çağrımları aritmetik operatörlerden, type castlerden ve rangeTo operatorden daha az önceliğe sahiptir
 * örn. 5 sumWith 4 * 3 = 5 sumwWith (4*3)
 */

infix fun Int.sumWith(a: Int): Int = this + a

/**
 * infix fonksiyonlar her zaman receiver a ve parametrenin belirtilmesine ihtiyacı vardır. güncel receiver içerisinde
 * çağırılırken this kullanılmalı veya parantezli fonksiyon çağırımı yapılmalıdır.
 */
class Operation {
    infix fun add(a: Int) {}

    fun buildAddOperation() {
        this add 5
        add(5)
        // add 5 bu yanlıştır
    }
}