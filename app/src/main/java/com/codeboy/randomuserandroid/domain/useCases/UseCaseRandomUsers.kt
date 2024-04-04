package com.codeboy.randomuserandroid.domain.useCases

import com.codeboy.randomuserandroid.domain.repository.RandomUserRepoDataStateSource
import javax.inject.Inject

class UseCaseRandomUsers @Inject constructor(private val repo: RandomUserRepoDataStateSource){

    public suspend operator fun invoke(page: Int, results: Int, seed: String)=repo.getRandomUsers(page, results, seed)

}