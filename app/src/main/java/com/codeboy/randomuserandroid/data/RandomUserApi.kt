package com.codeboy.randomuserandroid.data

import com.codeboy.randomuserandroid.domain.apiResponse.RandomUserApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET
    suspend fun getRandomUser(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String,
    ): Response<RandomUserApiResponse?>
}