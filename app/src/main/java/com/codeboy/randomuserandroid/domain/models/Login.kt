package com.codeboy.randomuserandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Login(

    @SerializedName("uuid")
    @Expose
    val uuid: String,

    @SerializedName("username")
    @Expose
    val username: String,

    @SerializedName("password")
    @Expose
    val password: String,

    @SerializedName("salt")
    @Expose
    val salt: String,

    @SerializedName("md5")
    @Expose
    val md5: String,

    @SerializedName("sha1")
    @Expose
    val sha1: String,

    @SerializedName("sha256")
    @Expose
    val sha256: String
) {

    constructor() : this(
        uuid = "",
        username = "",
        password = "",
        salt = "",
        md5 = "",
        sha1 = "",
        sha256 = ""
    )
}
