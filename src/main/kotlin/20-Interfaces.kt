/**
 * Interface ler sınıfların sahip oldukları ortak soyut (abstract) methodların
 * veya property lerin yer aldığı yapılardır. Sınıflar veya interfaceler
 * tarafından implemente edilebilir. Interface programming ile fake ler oluşturularak
 * test edilebilir. Interfaceler abstract sınıflar a benzer ancak birden fazla
 * sınıftan implemente edilir ve stateleri bulunmaz.
 */

interface Animal {
    fun eat()
    fun sleep()

    val type: Int
}

/**
 * Interface i implemente eden sınıflar bütün methodları override etmek zorundadır
 * Interfaceler içerisinde tanımlanan property lerin backing fieldları olamaz.
 *
 */
class Dog : Animal {
    override fun eat() {
        TODO("Not yet implemented")
    }

    override fun sleep() {
        TODO("Not yet implemented")
    }

    override val type: Int
        get() = 1
}

/**
 * Interface ler başka bir interface i implemente edebilirler. Bu durumda
 * kendilerini implemente eden sınıflar da override ettikleri
 * propertyleri bulundurma zorunda kalmazlar.
 */
interface BaseAnimalInterface {
    val type: Int
}

interface Pet : BaseAnimalInterface {
    val mappedType: String

    override val type: Int
        get() = parseType(mappedType)
}


private fun parseType(value: String): Int {
    return value.toInt()
}

/**
 * Bu durumda type ın override edilmesine gerek yoktur.
 * Ancak yine de type propertysine erişim sağlanır.
 */

data class Cat(override val mappedType: String) : Pet

/**
 * Sınıflar birden fazla interface i implemente edebilirler ve bu interfaceler
 * aynı isimde methodlara sahip olabilirler. Bunlardan hangilerinin çağırılacağını
 * bilmek için super<Class> şeklinde ön çağırım yapılır
 */

/**
 * Eğer bir interface methodunun body si yoksa o method abstract olarak
 * değerlendirilir ve kendisini implemente eden sınıftan direkt olarak
 * çağırılamaz
 */

interface Puppy {
    fun eat() {
        println("puppy eat")
    }

    fun sleep() {
        println("puppy sleep")
    }
}

interface Human {
    fun eat() {
        println("human eat")
    }

    fun sleep()
}

class Creature : Puppy, Human {
    override fun eat() {
        super<Puppy>.eat()
        super<Human>.eat()
    }

    override fun sleep() {
        super<Puppy>.sleep()
        // Abstract member cannot be accessed directly
        //super<Human>.sleep()
    }
}

fun main() {
    val pet = Cat("1")
    pet.type
}

/**
 * Uygulamalarda test yazarken genellikle fake, mock ve stub kavramları
 * karşımıza çıkar. Interfaceler fake oluştururken senaryoların
 * takip edileceği şekilde test yazılmak üzerine kurgulanır.
 */
interface Source {
    fun makeRequest(): String
}

class NetworkSource : Source {
    override fun makeRequest(): String {
        return "Hello"
    }
}

class TestFakeNetworkSource : Source {
    override fun makeRequest(): String {
        return "Actual"
    }
}