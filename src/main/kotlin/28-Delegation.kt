/**
 * Delegation bir nesnenin işini başka bir nesneye aktarmaktır. Örneğin sınıf B nin
 * A ya verilerek A sınıfında kendisine ait bir şey çağırmasının yetkisinin verilmesine
 * delegation denir.
 */

/**
 * Delegation explicit ve implicit olmak üzere ikiye ayrılır.
 */

/**
 * Explicit Delegation
 */

interface Destroyer {
    fun destroy()
}

class SpecialRemover : Destroyer {
    override fun destroy() {
        println("SpecialRemover.destroy()")
    }

}

open class UserView {
    open fun show() {
        println("View.Show()")
    }
}

class Screen(private val view: UserView, private val destroyer: Destroyer) {
    fun show() {
        view.show()
    }

    fun destroy() {
        destroyer.destroy()
    }
}

class CustomView : UserView() {
    override fun show() {
        println("CustomView.show()")
    }
}

/**
 * Kotlin delegation ı işleminin implicit olarak yapılmasını sağlar. Class Delegation
 * ve Delegated Properties feature larını sunar.
 */

interface Nameable {
    var name: String
}

class Halil : Nameable {
    override var name: String = "Halil"
}

class LongDistanceRunner : Runnable {
    override fun run() {
        println("run()")
    }
}

/**
 * Burada by ile delegation yapılmıştır. Yani verilen nameable a direkt olarak
 * erişilebilir. Runnable ın içerisindeki fonksiyonu yazmadan direkt olarak
 * erişilebilir.
 */
class PersonDelegation(name: Nameable, runner: Runnable) : Nameable by name, Runnable by runner

interface Printer {
    fun print()
}

class DesktopPrinter(private val text: String) : Printer {
    override fun print() {
        println(text)
    }
}

class User(printer: Printer) : Printer by printer

fun main() {
    /**
     * Screen in show işleminin View nesnesine atanması işlemi delegationdır.
     * Delegation işlemi aynı zamanda interfaceler aracılığıyla da yapılabilir.
     */
    val view = UserView()
    val customView = CustomView()
    val specialRemover = SpecialRemover()
    val screen = Screen(customView, specialRemover)
    screen.show()
    screen.destroy()

    val person = PersonDelegation(Halil(), LongDistanceRunner())
    println(person.name)
    println(person.run())

    val desktopPrinter = DesktopPrinter("Hello")
    User(desktopPrinter).print()
}