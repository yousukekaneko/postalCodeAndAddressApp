package com.example.android.sample.postalcodeandaddressapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/json")

    fun apiGet(
        @Query("method") method: Int
        ): Call<List<AddressResponse>>
}