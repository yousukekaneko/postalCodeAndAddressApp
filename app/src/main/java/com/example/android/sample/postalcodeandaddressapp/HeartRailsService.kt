package com.example.android.sample.postalcodeandaddressapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HeartRailsService {

    @GET("api/json")

    fun apiGet(
        @Query("method") method: String,
        @Query("postal") postal: String
        ): Call<List<AddressResponse>>
}