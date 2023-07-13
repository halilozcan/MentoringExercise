fun main() {
    callingFunction()
}

private val privateMemberVariable = "I am private"

val publicMember = "Public"

@PublishedApi
internal val internalMember = "I am internal"

fun callingFunction() {
    println("started")
    /**
     * Kotlin byte code a çevrildiğinde nesne üretilir. Bunu döngüde kullandığımız zaman
     * her bir çağırımda nesne üretilir. inline eklenmesi bunu engeller. ve fonksiyonun
     * direkt olarak içeriğinin kendisini çağıran tarafa kopyalanmasını sağlar.
     */
    higherOrderFunction {
        println("lambda")
        /**
         * inline fonksiyonlar lokal olarak return çağırarak calling site dan return
         * yapabilir.
         */
        // Sadece bu fonksiyondan dönülmesini istiyorsak return@higherOrderFunction
        return
    }

    higherOrderFunctionNoInlined(lambda = {
        return
    }, noInlinedLambda = {
        // no inlined return e izin vermez
        // return
    })

    higherOrderFunctionCrossInline {
        // non-local return e izin vermez.
        //return
    }

    println("finished")
}


inline fun higherOrderFunction(lambda: () -> Unit) {
    /**
     * inline fonksiyonlar kendisini çevreleyen sınıf veya scope daki
     * private değişkenlere ve methodlara etişim sağlayamaz. Erişmek için bunların public
     * veya internal olması, internal olanların @PublishedApi ile işaretlenmesi gerekir
     */
    //privateMemberVariable.length
    publicMember.length
    internalMember.length
    doSomething()
    lambda()
    doAnotherThing()
}

/**
 * Bazen bazı lambda expressionları inlined olmasını istemeyebilirsiniz. Eğer bunun
 * inlined olmasını istemiyorsanız noinline ile işaretleyebilirsiniz. noinline
 * fonksiyonlar local return lere izin vermez
 */
inline fun higherOrderFunctionNoInlined(lambda: () -> Unit, noinline noInlinedLambda: () -> Unit) {
    doSomething()
    noInlinedLambda()
    doAnotherThing()
}

/**
 * Local returne izin verilmesini istemediğimiz durumlar için crossinline ile
 * fonksiyonları işaretleriz.
 */

inline fun higherOrderFunctionCrossInline(crossinline lambda: () -> Unit) {
    /**
     * Inline bir fonksiyon içerisinde inline olmayan bir fonksiyona
     * higher order çağırılırsa return e izin vermemek için lambda() fonksiyonu
     * crossinline olarak işaretlenir.
     */
    normalFunction {
        lambda()
    }
}

/**
 * Eğer parametre olarak fonksiyon almayan bir fonksiyonuz inline olarak işaretlerseniz
 * lint alırsınız. Geniş fonksiyonları inline etmekten kaçının. Kotlin stdlib deki
 * fonksiyonları incelerseniz 1-3 satır arasında olduğunu görürsünüz.
 *
 * Inline fonksiyon kullanırken, fonksiyonlara ait referansı kullanmanıza izin verilmez.
 * Compiler Error alırsınız.
 */

fun callAction() {
    edit(false, lambda = {

    })
}

inline fun edit(isOk: Boolean, lambda: () -> Unit) {
    /**
     * Böyle bir kullanım yapamazsınız. Inline fonksiyonlara, fonksiyonlara
     * ait referansı geçiremezsiniz.
     * Bunu kullanmak için lambda yı noInline yapabilirsiniz ya da fonksiyonu inline
     * olmaktan çıkarabilirsiniz.
     *
     */
    // action(lambda)
}

fun action(lambda: () -> Unit) {

}

fun doSomething() {
    println("do something()")
}

fun doAnotherThing() {
    println("do another thing()")
}

fun normalFunction(lambda: () -> Unit) {
    println("normal function started")
    lambda()
    println("normal function ended")
    return
}
