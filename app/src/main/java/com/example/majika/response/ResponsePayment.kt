package com.example.majika.response

import com.google.gson.annotations.SerializedName

data class ResponsePayment(
    @SerializedName("status")
    var status: String,
)