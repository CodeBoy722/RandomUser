package com.codeboy.randomuserandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(

    @SerializedName("street")
    @Expose
    val street: Street,

    @SerializedName("city")
    @Expose
    val city: String,

    @SerializedName("state")
    @Expose
    val state: String,

    @SerializedName("country")
    @Expose
    val country: String,

    @SerializedName("postcode")
    @Expose
    val postcode: String?,

    @SerializedName("coordinates")
    @Expose
    val coordinates: Coordinates,

    @SerializedName("timezone")
    @Expose
    val timezone: TimeZone
) {

    constructor() : this(
        street = Street(),
        city = "",
        state = "",
        country = "",
        postcode = "",
        coordinates = Coordinates(),
        timezone = TimeZone()
    )


}
