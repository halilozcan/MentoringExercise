/**
 * Sınıf içerisinde sınıf tanımlanmasına nested class denir.
 * Nested class lar inner ile işaretlenmediği sürece static classlardır.
 * Bundan dolayı üst sınıfın üyelerine erişim sağlayamazlar. Direkt olarak . ile erişim sağlanabilir.
 */

/**
 * Inner ile işaretlenen nested class lar üst sınıfın üyelerine erişebilir.
 * inner class ile işaretlenmiş bir sınıfın içerisine sınıf nested class (static) yani
 * inner olmayan bir sınıf tanımlanamaz.
 */

class Outer {
    val name: String = ""

    inner class Nested {
        private val nestedName: String = ""

        inner class FuckingNested {
            init {
                name
            }

            fun getOuterName() = name
        }
    }
}

fun main() {
    val outer = Outer()
    /**
     * Eğer nested class inner olmazsa static olacağı için nesne üzerinden
     * değil de direkt olarak erişim sağlanması gerekir
     */
    // Outer.Nested()

    /**
     * Inner classlara nested üzerinden erişim sağlanması gerekir.
     */

    outer.Nested()

    /**
     * classın nesnesi üretilse bile kendisi çevreleyen outer class ın
     * üyelerine buradan erişim sağlanamaz.
     * inner class sadece kendisi içerisinden erişim sağlayabilir.
     * nested class static olduğu için zaten kendisi üzerinden erişim sağlayamaz
     */
    // outer.Nested().FuckingNested().name
}