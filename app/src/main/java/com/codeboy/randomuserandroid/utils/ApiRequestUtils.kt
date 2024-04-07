package com.codeboy.randomuserandroid.utils

import com.codeboy.randomuserandroid.utils.GeneralUtils.printLogD
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

object ApiRequestUtils {

    const val TAG = "API_REQUEST"
    fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)

        withTimeoutOrNull(60000L) {

            try {
                val response = call()

                if (response.isSuccessful) {
                    printLogD(TAG, "Response Body  "+response.body().toString())
                    val body = response.body()
                    if (body != null) {
                        printLogD(TAG, "********** API SUCCESS: $body")
                        emit(ApiResponse.Success(body))
                    } else {
                        printLogD(TAG, "********** API FAILLURE: $body")
                        emit(ApiResponse.Failure("Empty response body", response.code().toString()))
                    }
                } else {
                    response.errorBody()?.let { error ->
                        error.close()
                        val parsedError: ErrorResponse? =
                            Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                        if (parsedError != null) {
                            printLogD(TAG, "********** API Error: $parsedError")
                            emit(
                                ApiResponse.Failure(
                                    detail = parsedError.message ?: "",
                                    code = parsedError.code.toString()
                                )
                            )
                        } else {
                            emit(ApiResponse.Failure("Unknown error", response.code().toString()))
                        }
                    } ?: emit(ApiResponse.Failure("Unknown error", response.code().toString()))
                }
            } catch (e: Exception) {
                printLogD(TAG, "********** API Error: ${e.message ?: e.toString()}")
                e.printStackTrace()
                emit(ApiResponse.Failure(e.message ?: "Unknown error", "400"))
            }
        } ?: emit(ApiResponse.Failure(detail = "Cannot connect"))
    }.flowOn(Dispatchers.IO)

    suspend fun <T> apiRequest(call: suspend () -> Response<T>): ApiResponse<T> {

        return withTimeoutOrNull(60000L) {
            try {
                val response = call()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        printLogD(TAG, "********** API SUCCESS: $body")
                        ApiResponse.Success(body)
                    } else {
                        ApiResponse.Failure(
                            detail = "Empty response body",
                            code = response.code().toString()
                        )
                    }
                } else {
                    response.errorBody()?.let { error ->
                        error.close()
                        val parsedError: ErrorResponse? =
                            Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                        if (parsedError != null) {
                            printLogD(TAG, "********** API Error: $parsedError")
                            ApiResponse.Failure(
                                message = parsedError.message ?: "",
                                code = parsedError.code ?: "",
                                status = parsedError.status ?: "",
                                detail = parsedError.detail ?: ""
                            )
                        } else {
                            printLogD(TAG, "********** API Error: $parsedError")
                            ApiResponse.Failure(
                                detail = "Cannot parse error body",
                                code = "${response.code()}"
                            )
                        }
                    } ?: (ApiResponse.Failure(
                        detail = "Empty error body",
                        code = "${response.code()}"
                    ))
                }
            } catch (e: Exception) {
                printLogD(TAG, "********** API Error: ${e.message ?: e.toString()}")
                e.printStackTrace()
                ApiResponse.Failure(detail = e.message ?: "Unknown error", code = "")
            }
        } ?: ApiResponse.Timeout
    }
}