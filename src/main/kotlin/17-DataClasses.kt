/**
 * Data classlar adından anlaşılacağı üzere veri tutmak için kullanılan sınıflardır
 * Okunabilir çıktı üretme, instance ları karşılatırma-kopyalama gibi bir sürü
 * özelliği kendisinde otomatik olarak bulundururlar
 *
 * Data classlar data keyword ü ile işaretlenir.
 */

/**
 * Primary constructor ın en az bir parametresi olmalıdır.
 * Primary constructor ındaki bütün parametreler ya da var olarak işaretlenmelidir.
 * Data classlar abstract, open, sealed veya inner class olamazlar.
 */

data class PersonData(val name: String, val age: Int)

/**
 * Eğer JVM tarafında data classlar parametresiz yapılandırıcıya ihtiyaç
 * duyuyorlarsa bütün parametrelerin default value su olması gereklidir.
 */

data class PersonParameterless(val name: String = "", val age: Int = 30)

/**
 * Compiler data classlar ın primary constructorında tanımlanan tüm
 * parametreleri alarak;
 *
 * equals ve hashCode fonksiyonunu üretirler
 * toString User(name=Halil,age=20) şeklinde çıktı sağlar
 * componentN şeklinde destructuring declarations sağlar.
 * copy ile propertylerin içeriklerinin kopyalanmasını sağlarlar.
 */

/**
 * Equals hashcode gibi methodlar sadece primary constructora bakarlar
 * Bu yüzden aynı isimle üretilen iki nesne farklı yaşlara sahip olsa da
 * equals kontrolü true olarak döner.
 */
data class Equality(val name: String) {
    var age: Int = 0
}

class PersonNotData(val name: String) {
    var age: Int = 0
}

/**
 * Data classlarda implicit olarak val değerlerini değiştirmek mümkündür.
 * Ancak bu nesnenin kendisini değiştirerek değil kopyasını oluşturarak
 * yapılır. Bu da data class üzerinden copy fonksiyonu çağırılarak yapılır.
 */

data class DataCopy(val name: String, val age: Int)

fun main() {
    val person = DataCopy("Halil", 30)
    val newPerson = person.copy(age = 31)
    println(person)
    println(newPerson)

    val equality = Equality("Halil")
    val anotherEquality = Equality("Halil")
    anotherEquality.age = 10

    println(equality == anotherEquality)

    val personNotData = PersonNotData("Halil")
    val anotherPersonNotData = PersonNotData("Halil")

    anotherPersonNotData.age = 10

    println(personNotData == anotherPersonNotData)

    println(personNotData)

    val personData = PersonData("Halil", 30)
    val (name, age) = personData
    println("Name:$name age:$age")
}