package com.example.majika

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("v1/menu")
    suspend fun getAllUsers(): Response<ResponseMenu>

    @POST("v1/payment/{code}")
    suspend fun postPayment(@Path("code") code: String)
}