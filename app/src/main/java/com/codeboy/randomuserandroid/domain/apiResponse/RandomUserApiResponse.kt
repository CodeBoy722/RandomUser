package com.codeboy.randomuserandroid.domain.apiResponse

import com.codeboy.randomuserandroid.domain.models.Info
import com.codeboy.randomuserandroid.domain.models.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RandomUserApiResponse(
    @SerializedName("results")
    @Expose
    val results: ArrayList<User>,

    @SerializedName("info")
    @Expose
    val info: Info
)
