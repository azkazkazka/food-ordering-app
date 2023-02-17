package com.example.majika.retrofit

class ApiUtils {
    companion object {
        private const val BASE_URL = "http://localhost/"

        fun getApiInterface(): ApiInterface {
            return RetrofitClient.getClient(BASE_URL).create(ApiInterface::class.java)
        }
    }
}