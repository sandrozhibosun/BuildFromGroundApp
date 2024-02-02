package com.example.buildfromgroundapp.utils.network

import retrofit2.Response

// T generic class which act like a placeholder
// out T covariance, only for output, all the types must be under T
sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkException: Boolean,
        val errorCode: Int? = null,
        val errorDescription: String? = null
    ) : Resource<Nothing>()
}

fun <T> Response<T>.toResource(): Resource<T> {
    return try {
        if (this.isSuccessful && this.body() != null) {
            Resource.Success(
                this.body()!!
            )
        } else {
            Resource.Failure(
                false,
                this.code(),
                this.message()
            )
        }
    } catch (throwable: Throwable) {
        Resource.Failure(
            true,
            null,
            throwable.message
        )
    }
}