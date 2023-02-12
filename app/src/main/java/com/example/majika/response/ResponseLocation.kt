package com.example.majika.response

import com.google.gson.annotations.SerializedName

data class LocationData (
    @SerializedName("name")
    var name: String,

    @SerializedName("popular_food")
    var popular_food: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("contact_person")
    var contact_person: String,

    @SerializedName("phone_number")
    var phone_number: String,

    @SerializedName("longitude")
    var longitude: Double,

    @SerializedName("latitude")
    var latitude: Double,
)

data class ResponseLocation(
    @SerializedName("data")
    var data: List<LocationData>,
    @SerializedName("size")
    var size: Int,
)
