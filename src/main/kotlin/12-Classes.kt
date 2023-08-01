/**
 * Sınıflar belirli bir iş grubuna ait ortak özelliklerin toplandığı yerdir
 */


class PersonEmpty

class PersonConstructor constructor(firstName: String)

/**
 * Eğer primary constructor herhangi bir annotation veya visibility modifiers
 * içermiyorsa constructor kelimesi silinebilir.
 */
class PersonWithoutConstructor(firstName: String)

/**
 * Primary constructorlar herhangi bir şekilde kod içeremez. Init etme
 * işlemleri init blockları içerisinde yapılır
 * init işlemleri hangi sıradaysa o sırada handle edilir.
 */

/**
 * Constructorlar da val veya var kullanılabilir. val ya da var kullanılmazsa
 * init blockları haricinde görünmez olur.
 */

class PersonInitializer(name: String, lastName: String, isStudent: Boolean = true) {
    val variable1 = "Variable 1".also(::println)

    init {
        println("first init block:$name")
    }

    val variable2 = "Variable 2".also(::println)

    init {
        println("second init block:$name")
    }
}

class Customer(name: String) {
    val pass = name.uppercase()
}

class PersonLecture(val lectures: MutableList<LectureWithPerson> = mutableListOf())

class LectureWithPerson {
    constructor(person: PersonLecture) {
        person.lectures.add(this)
    }
}

/**
 * initializer blockları constructorlardan daha önce çalışır
 */

class Lecture

class PersonConstructorInit(val name: String) {
    private val lectures: MutableList<Lecture> = mutableListOf()

    constructor(name: String, lecture: Lecture) : this(name) {
        this.lectures.add(lecture)
    }
}

/**
 * Eğer bir sınıfın üretilmesini istemiyorsanız constructor ını private
 * olarak işaretleyebilirsiniz
 */

class WontBeCreated private constructor()

/**
 * Eğer primary constructor ın bütün parametreleri default value ya sahipse
 * JVM parametresiz bir constructor üretir.
 */


fun main() {
    //val person = Person("halil", "özcan")
}
