package com.neilsayok.bluelabs.domain.util

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

sealed class Response<out T>(
    val statusCode: HttpStatusCode = HttpStatusCode.Processing
) {

    data class SuccessResponse<T>(
        val data: T? = null, val message: String? = null
    ) : Response<T>()


    data class ExceptionResponse<T>(
        val exception: String? = null,
        val response: HttpStatusCode? = HttpStatusCode.ExpectationFailed,
        val message: String? = null
    ) : Response<T>(HttpStatusCode.ExpectationFailed)

    data object Loading : Response<Nothing>(HttpStatusCode.Processing)
    data object None : Response<Nothing>(HttpStatusCode.Processing)

    fun isLoading(): Boolean {
        return this is Loading
    }

    fun isSuccess(): Boolean {
        return this is SuccessResponse
    }

    fun isError(): Boolean {
        return this is ExceptionResponse
    }

    override fun toString(): String {
        return when (this) {
            is SuccessResponse -> "SuccessResponse(data=$data, message=$message)"
            is ExceptionResponse -> "ExceptionResponse(exception=$exception, response=$response, message=$message)"
            Loading -> "Loading"
            None -> "None"
        }
    }

}


suspend inline fun <reified T> HttpResponse.getResponse(): Response<T> {

    //println(this)
    return try {
        when (status.getStatus()) {
            HttpStatus.Loading -> {
                Response.Loading
            }

            HttpStatus.Success -> {
                Response.SuccessResponse(
                    data = this.body(), message = "Success"
                )
            }

            else -> {
                Response.ExceptionResponse(
                    exception = this.status.description, response = status, message = "Failed"
                )
            }
        }
    } catch (e: Exception) {
        Response.ExceptionResponse(
            exception = e.message, message = "Failed"
        )
    }


}

sealed class HttpStatus() {
    data object Loading : HttpStatus()
    data object Success : HttpStatus()
    data object Error : HttpStatus()
    data object None : HttpStatus()
}

inline fun HttpStatusCode.getStatus(): HttpStatus {
    return when (this.value) {
        in (100 until 200) -> {
            HttpStatus.Loading
        }

        in (200 until 300) -> {
            HttpStatus.Success
        }

        in (300 until 400) -> {
            HttpStatus.None
        }

        else -> {
            HttpStatus.Error
        }
    }
}

