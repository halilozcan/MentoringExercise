/**
 * Sadece tek bir tipe bağımlı kalmamak için bir yapının birden fazla veri tipiyle
 * çalışmasını sağlamak için genericler kullanır
 * Genericler <> arasına tanımlama tarafında temsili bir harf ya da kelime kullanılarak
 * tanımlanır. Çağırma aşamasında ise tip belirtilir.
 */

/**
 * Burada T type ı kısaltmak için kullanılmıştır. Element, Type vb. anlamlı olacak
 * her kelime veya harf kullanılabilir.
 * Buradaki variance invariant tır yani tam olarak verilen tip gereklidir.
 */

fun <T> log(x: T) {
    println(x)
}

interface Logger<T> {
    fun log(x: T)
}

/**
 * Buradaki interface implemente edileceği zaman tip belirtilmelidir. Belirtilen tiple
 * beraber compiler x'in String olacağını bilir ve ona göre override eder.
 */
class StringLogger : Logger<String> {
    override fun log(x: String) {

    }
}

/**
 * Variance super type ile sub type ın arasındaki ilişkinin ne olacağına
 * karar verir.
 */

interface AnimalGeneric

interface DogAnimal : AnimalGeneric

interface AnimalGenericImplementation : AnimalGeneric

interface CatAnimal : AnimalGeneric

class Cavalier : DogAnimal

class British : CatAnimal

interface SuperType {
    fun match(subject: CatAnimal): DogAnimal
}

interface SubType : SuperType {
    /**
     * A subtype must accept at least the same range of types as its supertype declares.
     * A subtype must return at most the same range of types as its supertype declares.
     */
    override fun match(subject: CatAnimal): DogAnimal
}

class SubTypeImplementation : SubType {
    override fun match(subject: CatAnimal): Cavalier {
        return Cavalier()
    }
}

fun createSubType() = SubTypeImplementation()

open class A {
    //some methods & member variable
}

class B : A() {
    // some mehods & member variables
}

/**
 * Invariance; tam olarak belirlenen tipin kullanılmasıdır
 */
class InvarianceGenericClass<T : A> {

}

/**
 * Covariance; super type yerine subtype kullanılması
 * Sadece out position da kullanılabilir yani fonksiyonun return type ı olarak
 * kullanılabilir.
 * val property olarak kullanılabilir, var property olarak kullanılmaz.
 */
class CovarianceGenericClass<out T : A> {

}

/**
 * Contravariance; subtype yerine supertype ın kullanılması
 * Sadece in position da yani parametre olarak kullanılabilir
 * out position olarak kullanılamaz yani val ya da var propery olarak kullanılmaz
 */
class ContravarianceGenericClass<in T : A> {

}


/**
 * Genericler inheritance kavramı normalden farklıdır. Örneğin X sınıfı Y sınıfının alt
 * sınıfı olarak düşünülürse Array<X>, Array<Y> nin alt tpipi olarak kabul edilmez.
 * Bu durum variance yaparak çözülür.
 */

/**
 * Type nesnelerin paylaştıkları propertyleri tanımlar. Yani derleyiciye yazılımcının
 * veriyi nasıl kullanmayı planladığını söyler.
 *
 * Class ise bu tipin bir implementasyonudur.
 */

/**
 * Burada kitty isminden de anlaşılacağı üzere hem bir classtır hem de bir tipin ismidir.
 */
data class Kitty(val kittyName: String, val isTwoFingered: Boolean) : Mammal(kittyName) {/*fun eat() {}

    fun sleep() {}*/
}

data class Lion(val lionName: String) : Mammal(lionName) {/*fun eat() {}

    fun sleep() {}*/
}

open class Mammal(val name: String) {
    fun eat() {

    }

    fun sleep() {

    }
}

/**
 * Burada Mammal belirtilip Kitty gönderilebilmesinin sebebi List in out ile işaretlenip
 * covariance sağlamasıdır. Covariance gönderilen tipin alt tipinin de gönderilmesini
 * sağlar. Fonksiyon parametresi ile gönderilen parametre eşleşmediklerinde kabul
 * kriterleri, bunların en azından tanımlanan tipin alt türü olmasıdır.
 *
 * Covariance sadece tek yollu gider. Yani Lion Mammal in alt tipidir diyebiliriz ancak
 * Mammal bir Lion dur diyemeyiz.
 */
fun feed(elements: List<Mammal>) {
    elements.forEach {
        it.eat()
    }
}

/**
 * Contravariance, covariance ın tam tersidir. Yani Mammal bir Kitty derken
 * Kitty bir Mammaldır denilemez. Contravariance in ile işaretlenerek yapılır.
 */

/*fun feed(elements: List<Kitty>) {
    elements.forEach {
        it.eat()
    }
}

fun feed(elements: List<Lion>) {
    elements.forEach {
        it.eat()
    }
}*/

interface Device

class Computer : Device
class Telephone : Device

fun addComputer(list: MutableList<Any>) {
    list.add(Computer())
}

/**
 * Out ve in positionlarda constructorlar bu parametre olarak alma kuralına tabi
 * tutulmazlar.
 * Java nın aksine Kotlin tarafında declaration-site variance şeklinde sınıflar
 * yazılırken type parametreleri tanımlanabilir. Java da WildCards'a (*) ihtiyaç
 * bulunmaktadır. Böylece fazlasıyla boilerplate code yazılmamış olur.
 */
class ReadOnlyBox<out T>(private var item: T) {
    fun getItem(): T = item
}

class WriteOnlyBox<in T>(private var item: T) {
    fun setItem(newItem: T) {
        item = newItem
    }
}

interface SomeInterface<in P, out R> {
    fun someFunction(p: P): R
}

/**
 * Declaration Variance
 */
interface GenericInterface<out T> {
    fun insert(): T
}

/**
 * Use-Site Variance
 */

fun <T> copyData(source: MutableList<T>, destination: MutableList<in T>) {
    for (element in source) {
        destination.add(element)
    }
}

fun main() {
    /**
     * Genericler de type inference vardır. Burada x direkt olarak String olarak
     * değerlendirilir. Listeler de generic type lar vardır. İlk tanımlama
     * esnasında alınacak değerler verilerek type inferencela Listenin tipi
     * otomatik olarak belirtilir. Bu da ilk tanımlama yapıldıktan sonra compile-time
     * safety sağlar. Yani başka bir değer ataması yapılırsa compile time
     * da hata oluşur.
     */
    log("Hello")
    log(1)
    log('c')
    log(10.0f)

    /**
     * B a nın alt tipidir. Bundan dolay B de anın alt tipi olduğu için üretilebilir
     */
    val a: A = B()

    /**
     * Typelar uyuşmaz. Invariance demek bütün tam olarak verilen tipin kullanılması
     * demektir.
     */
    // val invariance1: InvarianceGenericClass<A> = InvarianceGenericClass<B>()
    // val invariance2: InvarianceGenericClass<B> = InvarianceGenericClass<A>()

    /**
     * Sınıfın alt tipinin kullanılabilmesi için out ile işaretlenmesi gereklidir.
     */
    val covarianceGenericClass: CovarianceGenericClass<A> = CovarianceGenericClass<B>()

    /**
     * Sınıfın üst tipinin kullanulabilmesi için in ile işaretlenmesi gereklidir.
     */
    val contravarianceGenericClass: ContravarianceGenericClass<B> = ContravarianceGenericClass<A>()

    /**
     * var kitty:Kitty -> Kitti sınıfının nesnesini bir değişken tanımlar.
     * var kitty:Kitty? -> Kitty nin aynı zamanda nullable bir tip oluşturabileceğini
     * belirtir.
     */

    /**
     * List type ı otomatik olarak Kitty olur
     */
    val kitties = listOf(
        Kitty("A", false), Kitty("B", false), Kitty("C", false)
    )

    val lions = listOf(Lion("D"), Lion("E"))

    // Covariance
    feed(kitties)
    feed(lions)

    val allElements = listOf(
        Kitty("Jerry", false),
        Kitty("Bae", true),
        Kitty("Alex", false),
        Lion("Tegan"),
        Lion("Peggy")
    )

    // Contravariance
    val compareNames = Comparator { o1: Mammal, o2: Mammal ->
        o1.name.first().code - o2.name.first().code
    }

    println(allElements.sortedWith(compareNames))

    /**
     * Bu eklemenin yapılamamasının sebebi MutableList in invariant olmasıdır.
     * Invariance sadece aynı tipi kabul eder. Burada MutableList invariant
     * olmasaydı bir Exception fırlatılması söz konusu olurdur.
     */

    /**
     * Class = Telephone
     * Types = Telephone, Telephone?
     * Subtypes = Telephone inherit eden herhangi bir şey ya da Telephone un kendisi
     */

    /**
     * A -> A?
     * Telephone -> Telephone?
     * Telephone? -> Telephone // burası error verir.
     *
     * Nullable değer kabul eden bir değişken içerisinde null olmayan bir değer
     * tutulabilir ama tam tersi olamaz.
     */
    val telephones = mutableListOf(
        Telephone(), Telephone(), Telephone()
    )
    // addComputer(telephones)

    println(telephones)

    val computers = mutableListOf(
        Computer(),
        Computer(),
        Computer()
    )

    val devices = mutableListOf(
        Telephone(),
        Telephone(),
        Computer()
    )

    copyData(computers, devices) // List<Device> is subtype of List<Computer>
    copyData(telephones, devices) // List<Device> is subtype of List<Telephone>

    /**
     * Compile hatası verir. Çünkü Computer ve Telephone arasında bir typing yoktur
     */
    // copyData(computers, telephones)

    val subType = createSubType()
    val superType = subType as SuperType

    val dog = superType.match(British())
    val cavalier = subType.match(British())
}