package com.codeboy.randomuserandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Street(

    @SerializedName("number")
    @Expose
    val number: Int,

    @SerializedName("name")
    @Expose
    val name: String
) {

    constructor() : this(
        number = 0,
        name = ""
    )
}
