/**
 * Enum class lar durum tiplerini handle etmek için kullanılan yapılardır. Her bir
 * durum için bir sabit oluşturarak kullanım yapılır.
 */

/**
 * Enum class lar yapılandırıclarında bir property bulundururlarsa bütün sabitler de
 * aynı parametre sahip olmak durumundadırlar.
 */

/**
 * Enum claslar sınıfları kalıtım olarak alamazlar ancak interfaceleri implemente
 * edebilirler. Implemente edilen interface in methodları ya bütün sabitlerde
 * ya da enum class ın içerisinde override edilmelidir.
 *
 * Enum classlar inherit edilemezler.
 */

// Non-ordinal enum
enum class Side(var takenDistance: Int) : Move, Direction {
    LEFT(0) {
        override fun getEncounter() = RIGHT
        override fun walk(distance: Int) {
            takenDistance += distance
        }

        override fun toString(): String {
            return "Side is left: Walken distance:$takenDistance"
        }
    },
    RIGHT(0) {
        override fun getEncounter() = LEFT
        override fun walk(distance: Int) {
            takenDistance += distance
        }
    },

    UP(0) {
        override fun getEncounter() = DOWN
        override fun walk(distance: Int) {
            takenDistance += distance
        }
    },
    DOWN(0) {
        override fun getEncounter() = UP
        override fun walk(distance: Int) {
            takenDistance += distance
        }
    };

    /**
     * Enum sabitleri kendi var olan anonymous fonksiyonları override ederek
     * kendileri fonksiyon tanımlayabilir.
     */
    abstract fun getEncounter(): Side

    override fun goTo(distance: Int) {
        walk(distance)
    }
}

interface Move {
    fun walk(distance: Int)
}

interface Direction {
    fun goTo(distance: Int)
}

enum class WeekDay {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

fun main() {
    println(Side.valueOf("LEFT"))

    // Get all values
    Side.values().forEach {
        println(it)
    }

    // Get position
    println(Side.LEFT.ordinal)
}