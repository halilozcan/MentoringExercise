import java.lang.reflect.Constructor
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Reflection yazılan kodun yapısının çalışma zamanında incelenmesine olanak
 * sağlayan bir yapıdır. Örneğin bir property nin tipini öğrenme, private
 * ise public yapma gibi işlemler yapılabilir.
 */

class ReflectionClass

/**
 * Fonksiyonlara da reflection ile ulaşılabilir. Fonksiyonlar, propertyler
 * ve constructorlar callable referance olarak geçerler ve ortak süper
 * tipleri KCallable<out R> olarak geçer.
 *
 * Fonksiyon referansları KFunction<out R> nin alt tiplerinden biridir.
 * Bu durum parametre sayısına göre değişir.
 * Örneğin; KFunction3<T1,T2,T3,R>
 */

fun isOdd(x: Int) = x % 2 == 0

/**
 * Eğer bir sınıfın üyesi veya extension fonksiyonu kullanılmak
 * istenirse; String::toCharArray
 */

val isEmptyStringList: List<String>.() -> Boolean = List<String>::isEmpty

fun <A, B, C> unionConditions(f: (B) -> C, g: (A) -> B): (A) -> C {
    return {
        f(g(it))
    }
}

fun length(s: String) = s.length

var counter = 1

data class UserData(var name: String)

class Empty

fun createEmpty(initializer: () -> Empty) {
    val empty = initializer()
}

class ClassWithPrivateInstructor private constructor()

class ClassWithPrivateProperty {
    private val name: String = "Hello From Private Property"
}

fun createPrivateClassWithReflection(): ClassWithPrivateInstructor {
    return (ClassWithPrivateInstructor::class.java.declaredConstructors[0].apply {
        isAccessible = true
    } as Constructor<ClassWithPrivateInstructor>).newInstance()
}

fun main() {
    /**
     * Sınıf referansını alma
     * Burada dönen tip KClass şeklindedir. Kotlin Class Java class
     * referansı ile aynı ile değil.
     */
    val reflectionKotlin = ReflectionClass::class
    println(reflectionKotlin.constructors.size)
    println(reflectionKotlin.isOpen)

    /**
     * Kotlin Class Java class referansı ile aynı ile değildir. JVM
     * tarafındaki sınıfı almak için .java kullanılmalıdır.
     */
    val reflectionJava = ReflectionClass::class.java
    println(reflectionJava.constructors.size)

    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))

    val oddLength = unionConditions(::isOdd, ::length)
    val names = listOf("Halil", "Metehan", "Serdar", "İbrahim")
    println(names.filter(oddLength))

    println(::counter.get())
    println(::counter.name)
    ::counter.set(2)

    /**
     * Property referansı tek bir parametre bekleyen fonksiyonlarda
     * kullanılabilir
     */
    println(names.map(String::length))

    val name = UserData::name
    println(name.get(UserData("Halil")))

    /**
     * Constructorsız bir sınıfın reflection ile kullanımı
     */
    createEmpty(::Empty)

    val privateClass = createPrivateClassWithReflection()

    val classWithPrivateProperty = ClassWithPrivateProperty()

    val field = ClassWithPrivateProperty::class.memberProperties.find {
        it.name == "name"
    }

    field?.let {
        it.isAccessible = true
        val w = it.get(classWithPrivateProperty)
        println(w)
    }
}