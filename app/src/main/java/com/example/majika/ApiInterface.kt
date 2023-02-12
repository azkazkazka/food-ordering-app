package com.example.majika

import com.example.majika.response.ResponseLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("v1/menu")
    fun getAllUsers(): Call<ResponseMenu>

    @POST("v1/payment/{code}")
    fun postPayment(@Path("code") code: String)

    @GET("v1/branch")
    fun getAllBranch(): Call<ResponseLocation>
}