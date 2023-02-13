package com.example.majika.response

import com.google.gson.annotations.SerializedName

data class MenuData(
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

data class ResponseMenu(
    @SerializedName("data")
    var data: List<MenuData>,
    @SerializedName("size")
    var size: Int,
)