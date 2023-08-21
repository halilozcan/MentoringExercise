/**
 * Bazen bir sınıfı üretmek istediğimizde o sınıfa ait küçük
 * bir değişiklik yaparak kullanmak isteriz. Bu tür durumlarda sınıfı
 * üretmek ve kullanmamıza yarayan yapılar object expressionslar ve object
 * declarationslardır.
 */

/**
 * Object expressionslar anonymous sınıfların nesnelerini üretir. Aynı
 * zamanda class declaration ı olmadan sınıf tanımlamamızı sağlar. Bu
 * sınıflar sadece tek kullanımlık sınıflar olarak geçer. Anonymous
 * sınıfın nesneleri anonymous objects olarak adlandırılır.
 */

// object expressionslar object anahtar kelimesi ile başlar

val sum = object {
    val number1 = 1
    val number2 = 2

    // object Any i kalıtım aldığından dolayı toString() override edilebilir.
    override fun toString(): String {
        return "Sum is:${number1 + number2}"
    }
}

/**
 * Object declarations genelde singleton oluşturmak için kullanılır.
 * Singleton Pattern: tek bir nesne üretme ve onu kullanma
 */

object Database {

    /**
     * Double checked locking her zaman garanti sağlamaz. Volatile
     * anahtar kelimesi derleyiciye bu değişkenin asenkron çalışan
     * threadler tarafından değiştirilebileceğini söyler.
     */
    @Volatile
    private var connection: Connection? = null

    @JvmStatic
    fun getConnection(): Connection {
        if (connection == null) {
            /**
             * Herhangi bir zamanda başka bir thread instance ı create
             * ederken bir thread ilk check i geçebilir. Bundan dolayı
             * senkronize olacak şekilde başka bir null kontrol check i
             * konulmalıdır. Burada connection null ise, sychronized
             * anahtar kelimesi buraya kilitler ve ikinci kontrol durumunda
             * instance ın null olduğunu garanti eder. Eğer nesne null
             * ise nesne oluşturulur.
             */
            synchronized(Database::class.java) {
                if (connection == null) {
                    connection = Connection()
                }
            }
        }
        return connection!!
    }
}

class Connection

abstract class EventListener {
    abstract fun onClick()
    abstract fun onDoubleClick()
    abstract fun onTouch()
}

interface BluetoothListener {
    fun onConnected()
    fun onLowEnergy()
    fun onDisconnected()
}

fun main() {
    println(sum)

    val deviceName = "BlueMeda"

    /**
     * Başka bir nesnesi oluşturulmadan anlık olarak object expression
     * ile anonymous class ın işlevini görmesi sağlanır.
     */
    val eventListener = object : EventListener() {
        override fun onClick() {

        }

        override fun onDoubleClick() {

        }

        override fun onTouch() {

        }
    }

    val bluetoothListener = object : BluetoothListener {
        override fun onConnected() {
            println("Connected. We are in at $deviceName!")
        }

        override fun onLowEnergy() {
            println("Fuckk! Low Energy. Please charge $deviceName")
        }

        override fun onDisconnected() {
            println("Pfff. We are kicked from $deviceName!")
        }
    }

    val bluetoothService = BluetoothService().apply {
        setBluetoothListener(bluetoothListener)
    }

    bluetoothService.connect()
    bluetoothService.informLowEnergy()
    bluetoothService.disconnect()
}

class BluetoothService {

    private var bluetoothListener: BluetoothListener? = null

    fun setBluetoothListener(bluetoothListener: BluetoothListener?) {
        this.bluetoothListener = bluetoothListener
    }

    fun connect() {
        bluetoothListener?.onConnected()
    }

    fun disconnect() {
        bluetoothListener?.onDisconnected()
    }

    fun informLowEnergy() {
        bluetoothListener?.onLowEnergy()
    }
}