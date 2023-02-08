package com.example.majika

import com.google.gson.annotations.SerializedName

data class ResponseMenu(
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("sold")
    var sold: Int,
    @SerializedName("type")
    var type: String,
)
