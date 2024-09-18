/**
 * Bir sınıftan başka bir sınıf türetilmesi işlemine inheritance(kalıtım) denir.
 * Kotlin de bütün sınıflar implicit olarak Any i kalıtım alır.
 */

class Test // implicitly inherits Any

/**
 * Kotlinde tüm sınıflar default olarak finaldır ve inherit edilemezler. Bundan
 * dolayı başlarına open kelimesinin koyulması gerekir.
 */
open class Base(val number: Int)

class Derived : Base(10)

/**
 * Eğer base sınıfın bir parametresi varsa onu kalıtım alan sınıf işlem sırasında
 * base sınıfa bu parametreyi vermelidir
 */

open class BaseWithSecondaryConstructor() {
    constructor(number: Int) : this()

    constructor(number: Int, age: Int) : this()
}

class DerivedWithSecondaryConstructor : BaseWithSecondaryConstructor {
    constructor(number: Int) : super(number)
    constructor(number: Int, age: Int) : super(number, age)
}

/**
 * Kotlin methodların da override edilmesi için open keyword ü kullanılır.
 */

open class Pen {
    open val brand: Int = 0
    open fun draw() {
        println("pen drawing")
    }

    fun changeColor() {
        println("pen change color")
    }
}

// Parent class default argumente sahip olsa bile override edildiğinde normal argumente çevrilirler.
open class Parent {
    open fun sum(a: Int, b: Int = 0): Int {
        return a + b
    }
}

class Child : Parent() {
    override fun sum(a: Int, b: Int): Int {
        return super.sum(a, b)
    }
}

class Pencil : Pen() {

    /**
     * Open olmayan bir class daki open bir property nin kalıtıma herhangi
     * bir etkisi yoktur. Yani kalıtım yapılamaz.
     */
    open val height: Int = 180
    override fun draw() {
        println("pencil drawing")
    }

    /**
     * Eğer kalıtım alınan sınıftaki method open değilse kalıtım alan sınıf
     * aynı signature ile bu methodu yazamaz.
     * fun changeColor(){
     *
     * }
     */

}

open class BallPointPen : Pen() {
    /**
     * Override edilen bir üye tekrardan başka bir sınıf tarafından override
     * edilebilir. Bunu yasaklamak için final keywordü kullanılır.
     */
    final override fun draw() {
        println("ball point pen drawing")
    }

    override val brand: Int = 1
    // üst sınıftaki değer alınmak isteniyorsa
    // override val brand: Int  get() = super.brand
}

/**
 * Initialize işleminde ilk önce derived classın base class a parametre
 * verdiği yer çalışır. Daha sonra base class ın init i ve propertyleri üzerinde
 * çalışır. Ardından derived class ın initi ve propertyleri üzerinde sırasıyla
 * çalışma gerçekleşir.
 */

open class BaseInitialization(val name: String) {
    init {
        println("init in base")
    }

    open val nameLength: Int = name.length.also {
        println("nameLength in base")
    }
}

class DerivedInitialization(name: String) : BaseInitialization(
    name.also { println("argument initialization") }
) {
    init {
        println("init in derived")
    }

    override val nameLength: Int = super.nameLength.also {
        println("nameLength in derived")
    }
}

class SpecialPen : Pen() {
    override fun draw() {
        println("special pen drawing")
        val painter = Painter()
        painter.drawAndWrite()

    }

    inner class Painter {
        private fun write() {
            println("painter is writing")
        }

        fun drawAndWrite() {
            super@SpecialPen.draw()
            write()
        }
    }
}


fun main() {
    val derived = DerivedInitialization("Halil")

    val specialPen = SpecialPen()
    specialPen.draw()
}