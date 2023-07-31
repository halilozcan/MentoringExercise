/**
 * val ile tanımlama yapıldığı zaman sadece getter ı oluşturulur
 * var ile tanımlama yapıldğı zaman getter ve setter ı oluşturur. kendi içerisinde
 * encapsulation yapılır.
 */

class Person(val name: String, var lastName: String, val born: Int) {

    /**
     * Burada böyle bir tanımlama compile olmadan hata vermez. Getter ve setter
     * fonksiyonlar val ve var durumuna göre oluştuğu için get ve set isimli
     * fonksiyonlar tanımlanamaz
     */
    // fun getName() = name

    /**
     * Custom bir şekilde getter oluşturabilirsiniz. Property e her eriştiniz
     * durumda getter fonksiyonu çağırılır. Bu durumda bir field üretilmez ve
     * memory de yer almaz.
     */
    val fullName: String get() = "$name $lastName"

    var info
        get() = "$name $lastName $born"
        set(value) {
            parsePersonInfo(value)
        }

    /**
     * Eğer bir property nin değerinin dışarıdan okunup ama dışarıdan
     * değiştirilmesini istemiyorsanız private setter kullanabilirsiniz.
     */
    var gender: String = ""
        private set

    fun setGenderInternally(gender: String) {
        this.gender = gender
    }

    /**
     * Fieldlar bir sınıfın private memberlarıdır. Memory i kullanırlar
     * Propertyler private fieldlara erişmeye izin veren getter ve setter
     * fonksiyonlarıdır. Aslında her şey bir propertydir.
     */

    /**
     *
     */
    var height = 0
        set(value) {
            if (value >= 0) {
                // arkaplandaki fieldı şartlı olarak değiştiriyoruz.
                field = value
                // counter = value ifadesine izin verilmez. recursive atama olur.
            }
        }

    /**
     * Backing property kullanımında arkaplanda grades değil sadece _grades
     * üretilir. grades e ulaşım sağlandığı zaman bir getter fonksiyonu oluşturulur.
     * ve _grades döndürülüyor
     */
    private var _grades: IntArray? = null
    val grades: IntArray
        get() {
            if (_grades == null) {
                _grades = intArrayOf()
            }
            return _grades ?: throw Exception("failed")
        }
}

private fun parsePersonInfo(value: String) = ""

fun main() {
    println("hello")

    val person = Person("Halil", "Özcan", 1994)

    person.info = ""

    person.height = 180

    println(person.height)

    person.grades
}