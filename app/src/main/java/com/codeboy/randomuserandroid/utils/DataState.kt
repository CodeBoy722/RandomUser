package com.codeboy.randomuserandroid.utils

sealed class DataState<out T> {

    data class IDLE<out T>(val data: T?=null) : DataState<T>()
    data class Success<out T>(val data: T?) : DataState<T>()
    data class Loading<out T>(val partialData: T? = null) : DataState<T>()
    data class Failure<out T>(val throwable: Throwable? = null, val partialDataFailure: T? = null) :
        DataState<T>()

    val extractData: T?
        get() = when (this) {
            is Success -> data
            is IDLE -> data
            is Loading -> partialData
            is Failure -> partialDataFailure

        }
}

fun <T, R> DataState<T>.map(transform: (data: T?) -> R): DataState<R> {
    return when (this) {
        is DataState.Success -> DataState.Success(transform(this.data))
        is DataState.IDLE -> DataState.IDLE(transform(this.data))
        is DataState.Failure -> DataState.Failure(this.throwable)
        is DataState.Loading -> DataState.Loading(transform(this.partialData))
    }
}