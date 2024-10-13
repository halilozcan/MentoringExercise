import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 * Durum tiplerini belirtmek için kullanılır. Android tarafında en önemli kullanımından birisi Response un state ini
 * belirtmektir. Enumlar dan en büyük farkı her bir state in kendisine
 * ait bir veri tutabilmesidir.
 */

sealed class UserClickAction {
    data class JustIdContentClick(val id: String) : UserClickAction()
    data class MediaContentClick(val id: String, val imageUrl: String, val mediaContentId: String) : UserClickAction()
}

fun handleUserClickAction(userClickAction: UserClickAction) {
    when (userClickAction) {
        is UserClickAction.JustIdContentClick -> {
            println("Just Id:" + userClickAction.id)
        }

        is UserClickAction.MediaContentClick -> {
            println("Media:" + userClickAction.imageUrl)
        }
    }
}

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data object Loading : Response<Nothing>()

    sealed class Error : Response<Nothing>() {
        data object NetworkError : Error()
        data object WritingError : Error()
    }
}

data class ResultDto(val data: String)

fun main() = runBlocking {
    val userAction = UserClickAction.JustIdContentClick("12")
    // handleUserClickAction(userAction)

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
            throw NetworkException("Something went wrong")
        } else {
            emit(Response.Success(ResultDto("hello")))
        }
    } catch (e: Exception) {
        when (e) {
            is NetworkException -> {
                emit(Response.Error.NetworkError)
            }

            is WritingException -> {
                emit(Response.Error.WritingError)
            }
        }
    }
}

class NetworkException(override val message: String) : Exception(message)
class WritingException(override val message: String) : Exception(message)