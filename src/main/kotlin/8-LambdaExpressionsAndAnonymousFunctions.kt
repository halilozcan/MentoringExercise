typealias SumFunction = (Int, Int) -> Int

fun main() {

    val names = arrayOf("Halil", "Metehan", "İbrahim", "Hilal")

    /**
     * Lambda expressionlar ve anonymous fonksiyonlar functions literals olarak adlandırılır.
     * Function literals bir fonksiyonun tanımlanmadığı ama bir ifade olarak anlık olarak
     * geçirildiği anlamına gelir.
     */

    /**
     * Fonksiyonlar parametre tipleri ve return tipi belirtilerek aşağıdaki gibi tanımlanır.
     *
     * Burada (Int, Int) -> Int
     * parantez içerisindeki Int ve Int iki tane int tipinde paramtre olacağını ve Int dönüş
     * tipinin olacağını belirtir
     *
     * (A,B)-> C
     * A -> Parametre Tipi
     * B -> Parametre Tipi
     * C -> Return Tipi
     *
     * () -> A - parametresi yok A tipinde dönüşü var
     *
     * A.(B) -> C -> A tipi üzerinden B parametreli fonksiyon çağrımı var ve return tipi
     * C demektir.
     *
     * (x:Int,y:Int) -> Point
     *
     * nullable fonksiyon tanımlamak için ((Int,Int)->Int)?
     */

    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

    /**
     * Lambda expressionlar her zaman süslü parantezlerle çevrilir.
     * parametre tanımlaması yapılırken virgül ile ayrılır tip belirtmesi zorunlu değildir.
     * body si -> işaretinden sonra yazılır. Eğer dönüş tipi Unit değilse body nin son ifadesi
     * return value olarak değerlendirilir.
     */

    sum.invoke(3,5)

    val sumLeaved = { x: Int, y: Int -> x + y }

    /**
     * bir lambda expression (yani function) bir fonksiyonun son parametresi olarak
     * tanımlandıysa fonksiyon parantezinden sonra süslü parantez ile ayrılabilir.
     * Buna trailing lamdba denilebilir
     */

    // Örnek süslü parantez kullanımı. Fold un son parametresi
    names.fold("") { acc, e ->
        acc + e
    }

    run {
        println("run..")
    }

    /**
     * lambda expression için tek bir parametre olması oldukça yaygın bir kullanımdır.
     * bu durumda lambda expression kullanılırken -> işareti göz ardı edilebilir.
     * Bu parametre implicit olarak it şeklinde adlandırılır.
     */

    names.filter {
        it.length > 4
    }

    // Returning Value From Lambda Expression
    names.filter {
        val shouldFilter = it.length > 5
        shouldFilter
    }

    names.filter {
        val shouldFilter = it.length > 5
        return@filter shouldFilter
    }

    /**
     * Lambda Expressionlar fonksiyonlar dışında süslü parantez ile kullanılabildiğinde
     * sıralı şekilde LINGQ-style şeklinde kullanılabilir.
     */
    names.filter {
        it.length == 5
    }.sortedBy {
        it
    }.map {
        it.toUpperCase()
    }

    // kullanılmayacak parametreler _ ile gösterilebilir
    names.forEachIndexed { _, s ->
        println(s)
    }

    /**
     * Lambda expressionlar fonksiyonun return type ını belirtemezler. return type açık
     * olarak belirtmek için anonymous fonksiyonlar kullanılır. anonymous fonksiyonlar
     * isimsiz olarak yazılan fonksiyonlardır. bir body olarak veya single expression
     * olarak yazılabilir. return type aynı zamanda infer edilebilir. yani belirtmeye de
     * gerek yoktur
     */

    val filterFunction = fun(a: String): Boolean = a.length > 5

    names.filter(filterFunction)

    val function = fun(x: Int, y: Int): Int = x + y

    function.invoke(2,3)

    /**
     * Lambda expressionlar veya local fonksiyonlar kendilerinin üst kapsamına ulaşabilir.
     * buna kendisinin closure ı denir. bunlar değişkenler de olabilir.
     */

    var sumOfClosure = ""

    names.filter {
        it.length > 0
    }.forEach {
        sumOfClosure += it
    }

    println(sumOfClosure)

    /**
     *
     */
}

fun typeAliasFunction(action:SumFunction):Int{
    return action.invoke(1,2)
}





