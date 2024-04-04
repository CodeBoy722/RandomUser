package com.codeboy.randomuserandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimeZone(

    @SerializedName("offset")
    @Expose
    val offset: String,

    @SerializedName("description")
    @Expose
    val description: String
) {

    constructor() : this(
        offset = "",
        description = ""
    )
}
