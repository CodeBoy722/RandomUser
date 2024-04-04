package com.codeboy.randomuserandroid.domain.repository
import com.codeboy.randomuserandroid.data.RandomUserApi
import com.codeboy.randomuserandroid.domain.models.User
import com.codeboy.randomuserandroid.utils.ApiRequestUtils
import com.codeboy.randomuserandroid.utils.ApiResponse
import com.codeboy.randomuserandroid.utils.DataState
import javax.inject.Inject

class RandomUserRepoDataStateSource @Inject constructor(private val apiService:RandomUserApi) {

    suspend fun getRandomUsers(
        page: Int,
        results: Int,
        seed: String
    ): DataState<List<User>?> {

       val stateApiResponse = ApiRequestUtils.apiRequest {
            apiService.getRandomUser(page, results, seed)
        }

        return when (stateApiResponse) {

            is ApiResponse.Loading -> {
                DataState.Loading(null)
            }

            is ApiResponse.Success -> {
                DataState.Success(stateApiResponse.data?.results)
            }

            is ApiResponse.Failure -> {
                DataState.Failure(throwable = Throwable(stateApiResponse.message))
            }

            is ApiResponse.Timeout -> {
                DataState.Failure(throwable = Throwable("TIMEOUT"))
            }
        }
    }

}