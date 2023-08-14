/**
 * Abstract classlar initiate edilemezler sadece kalıtım alınabilirler. Abstract classlar
 * hem abstract fonksiyonları(bodysiz) veya body li methodları içerebilirler.
 *
 * Bir abstract class kendini kalıtım alan sınıflara ortak interfaceleri veya
 * implementasyonları sunmak için kullanılır.
 *
 * Kendisini kalıtım alan sınıflar abstract olan bütün fonksiyonları override
 * etmek zorundadırlar.
 */

/**
 * Nesnesi üretilemez.
 * Bütün propertyler ve üye fonksiyonlar default olarak abstract değildirler.
 * Eğer bu fonksiyonlar override edilmek istenir abstract sınıfta open olarak
 * işaretlenmelidir.
 * Eğer fonksiyon abstract olarak işaretliyse open anahtar kelimesine gerek yoktur.
 * Varsayılan olarak opendırlar.
 *
 */

/**
 * Abstract class template sınıftırlar. Bir taslak gibi davranırlar.
 * Diğer sınıflarla beraber bir is-a relationships with other classes.
 *
 *
 */
abstract class AbstractClass

abstract class Employee(val name: String, val experience: Int, open val dateOfBirth: Int) {


    abstract var salary: Double

    abstract fun dateOfBirth(): String

    fun printDetails() {
        println("Name:$name")
        println("Experience:$experience")
        println("Date of birth:${dateOfBirth()}")
    }
}

class SoftwareEngineer(name: String, experience: Int, override val dateOfBirth: Int) :
    Employee(name, experience, dateOfBirth) {
    override var salary: Double = 50000.00


    override fun dateOfBirth(): String {
        return "Birth date is:$dateOfBirth"
    }

}

/**
 * Abstract classlar interfacelerin bütün implementasyonlarının override edilmesini
 * işlemini kendilerine alarak kod fazlalığını engeller
 */

interface AnimationListener {
    fun onStart()
    fun onResume()
    fun onDestroy()
}

abstract class AnimationContract : AnimationListener {
    override fun onStart() {}
    override fun onResume() {}
    override fun onDestroy() {}
}

/**
 * Abstract classlar abstract fonksiyonlar aracılığıyla kendi üzerindeki
 * sorumluluğu kendini kalıtım alan sınıfa aktarır. Böylece kendini kalıtım
 * alan sınıf çalışan implementasyonu sağlar.
 */

abstract class Shape {
    abstract fun draw()

    abstract fun changeColor(newColor: Int)

    fun print() {
        /**
         * Draw sorumluluğunu kendisini kalıtım alan sınıfa aktarır.
         */
        println("Shape Print")
        draw()
    }

    fun updateColor(color: Int) {
        changeColor(color)
    }
}

class Circle : Shape() {

    private var drawColor: Int = 0

    override fun draw() {
        println("Circle Draw")
    }

    override fun changeColor(newColor: Int) {
        println("Change Color")
        drawColor = newColor
    }
}

class Rectangle : Shape() {

    private var drawColor: Int = 1

    override fun draw() {
        println("Circle Draw")
    }

    override fun changeColor(newColor: Int) {
        println("Change Color")
        drawColor = newColor
    }
}


fun main() {
    val softwareEngineer = SoftwareEngineer("Halil", 6, 1994)
    // softwareEngineer.printDetails()

    val animationContract = object : AnimationContract() {
        override fun onStart() {
            super.onStart()
        }
    }

    val circle: Shape = Circle()
    circle.updateColor(2)
    printShape(circle)
}

/**
 * Abstract classlar polymorphism sağlarlar. Yani shape nesnesi alan bir method
 * tanımlayıp kendisini kalıtım alan sınıfın nesnesinin gönderilmesi sağlanır.
 */
private fun printShape(shape: Shape) {
    shape.print()
}

