import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 * Durum tiplerini belirtmek için kullanılır. Android tarafında Response un state ini
 * belirtmek için kullanılır. Enumlar dan en büyük farkı her bir state in kendisine
 * ait bir veri tutabilmesidir.
 */

sealed class Response<out T : Any> {
    data class Success<T : Any>(val data: T) : Response<T>()
    data object Loading : Response<Nothing>()

    sealed class Error : Response<Nothing>() {
        data object NetworkError : Error()
        data object WritingError : Error()
    }
}

data class ResultDto(val data: String)

fun main() = runBlocking {
    println("Error will not be thrown")

    delay(2000)

    makeNetworkResponse().collect {
        println(it)
    }

    delay(2000)

    println()
    println("Error will be thrown")

    delay(2000)
    makeNetworkResponse(true).collect {
        println(it)
    }
}

suspend fun makeNetworkResponse(isError: Boolean = false) = flow {
    emit(Response.Loading)
    delay(2000)
    try {
        if (isError) {
            throw Exception("Something went wrong")
        } else {
            emit(Response.Success(ResultDto("hello")))
        }
    } catch (e: Exception) {
        emit(Response.Error.NetworkError)
    }
}