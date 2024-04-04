package com.codeboy.randomuserandroid.utils

sealed class ApiResponse<out T> {
    object Loading : ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ) : ApiResponse<T>()

    data class Failure(
        val status: String="",
        val code: String="",
        val message: String="",
        val detail: String="",
    ) : ApiResponse<Nothing>()

    object Timeout : ApiResponse<Nothing>()
}