package com.codeboy.randomuserandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("gender")
    @Expose
    val gender: String,

    @SerializedName("name")
    @Expose
    val name: Name,

    @SerializedName("location")
    @Expose
    val location: Location,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("login")
    @Expose
    val login: Login,

    @SerializedName("dob")
    @Expose
    val dob: Dob,

    @SerializedName("registered")
    @Expose
    val registered: Registered,

    @SerializedName("phone")
    @Expose
    val phone: String,

    @SerializedName("cell")
    @Expose
    val cell: String,

    @SerializedName("id")
    @Expose
    val id: Id,

    @SerializedName("picture")
    @Expose
    val picture: Picture,

    @SerializedName("nat")
    @Expose
    val nat: String
) {

    constructor() : this(
        gender = "",
        name = Name(),
        location = Location(),
        email = "",
        login = Login(),
        dob = Dob(),
        registered = Registered(),
        phone = "",
        cell = "",
        id = Id(),
        picture = Picture(),
        nat = ""
    )


}


