/**
 * Kotlinde toplama(+) çıkarma(-) çarpma (*) bölme(/) gibi hali hazırda operatörler
 * bulunmaktadır. Bu operatörler başka sınıflara da davranışlar kazandırabilir
 */

/**
 * x1 + x2	        x1.plus(x2)
 * x1 – x2	        x1.minus(x2)
 * x1 * x2	        x1.times(x2)
 * x1/ x2	        x1.div(x2)
 * x1 % x2	        x1.rem(x2)
 * x1..x2	        x1.rangeTo(x2)
 * x1 in x2	        x2.contains(x1)
 * x1 !in x2	    !x2.contains(x1)
 * x[i]	            x.get(i)
 * x[i, j]	        x.get(i, j)
 * x[i] = b	        x.set(i, b)
 * x[i, j] = b	    x.set(i, j, b)
 * x()	            x.invoke()
 * x(i)	            x.invoke(i)
 * x(i, j)	        x.invoke(i, j)
 * +x1              x1.unaryPlus()
 * -x1              x1.unaryMinus()
 * x1 += x2	        x1.plusAssign(x2)
 * x1 -= x2	        x1.minusAssign(x2)
 * x1 *= x2	        x1.timesAssign(x2)
 * x1 /= x2	        x1.divAssign(x2)
 * x1 %= x2	        x1.remAssign(x2)
 */

data class Point(val x: Int, val y: Int) {
    operator fun invoke() = "X:$x Y:$y"
}

operator fun Point.plus(other: Point): Point {
    return Point(x + other.x, y + other.y)
}

operator fun Point.minus(other: Point): Point {
    return Point(x - other.x, y - other.y)
}

class ShapeOperator {
    private val points = mutableListOf<Point>()
    operator fun Point.unaryPlus() {
        points.add(this)
    }
}

fun shape(init: ShapeOperator.() -> Unit): ShapeOperator {
    val shape = ShapeOperator()
    shape.init()
    return shape
}

fun main() {
    val point = Point(1, 2)
    val other = Point(2, 3)

    val sum = point + other

    println(sum)
    println(sum())

    shape {
        +Point(0, 0)
        +Point(0, 1)
        +Point(1, 3)
    }

    println("Hello" - 'e')

    println(-"Hello")
}

operator fun String.minus(char: Char): String {
    return dropLastWhile {
        it != char
    }
}

operator fun String.unaryMinus(): String {
    return this.reversed()
}