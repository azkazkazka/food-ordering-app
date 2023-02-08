package com.example.majika

import com.google.gson.annotations.SerializedName

data class ResponsePayment(
    @SerializedName("message")
    var message: String,
)
