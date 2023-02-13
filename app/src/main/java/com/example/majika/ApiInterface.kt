package com.example.majika

import com.example.majika.response.ResponseLocation
import com.example.majika.response.ResponseMenu
import com.example.majika.response.ResponsePayment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("v1/menu")
    fun getAllMenu(): Call<ResponseMenu>

    @GET("v1/menu/food")
    fun getAllFood(): Call<ResponseMenu>

    @GET("v1/menu/drink")
    fun getAllDrink(): Call<ResponseMenu>

    @POST("v1/payment/{code}")
    fun postPayment(@Path("code") code: String) : Call<ResponsePayment>

    @GET("v1/payment/success")
    fun getSuccessPayment() : Call<ResponseBody>

    @GET("v1/payment/failed")
    fun getFailedPayment() : Call<ResponseBody>

    @GET("v1/branch")
    fun getAllBranch(): Call<ResponseLocation>
}