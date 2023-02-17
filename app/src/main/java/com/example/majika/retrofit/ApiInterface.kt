package com.example.majika.retrofit

import com.example.majika.response.ResponseLocation
import com.example.majika.response.ResponseMenu
import com.example.majika.response.ResponsePayment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("v1/menu")
    suspend fun getAllMenu(): Response<ResponseMenu>

    @GET("v1/menu/food")
    suspend fun getAllFood(): Response<ResponseMenu>

    @GET("v1/menu/drink")
    suspend fun getAllDrink(): Response<ResponseMenu>

    @POST("v1/payment/{code}")
    suspend fun postPayment(@Path("code") code: String) : Response<ResponsePayment>

    @GET("v1/payment/success")
    suspend fun getSuccessPayment() : Response<ResponseBody>

    @GET("v1/payment/failed")
    suspend fun getFailedPayment() : Response<ResponseBody>

    @GET("v1/branch")
    suspend fun getAllBranch(): Response<ResponseLocation>
}