package com.codeboy.randomuserandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Registered(

    @SerializedName("date")
    @Expose
    val date: String,

    @SerializedName("age")
    @Expose
    val age: Int
) {

    constructor() : this(
        date = "",
        age = 0
    )
}
