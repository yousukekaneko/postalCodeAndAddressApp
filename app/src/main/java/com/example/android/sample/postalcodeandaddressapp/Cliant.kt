package com.example.android.sample.postalcodeandaddressapp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Cliant {

    val httpBuilder: OkHttpClient.Builder get() {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("accept", "application,/json")
                .method(original.method(), original.body())
                .build()
            var response = chain.proceed(request)

            return@Interceptor response
        })
            .readTimeout(30, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }

    fun createService() : HeartRailsService {
        var client = httpBuilder.build()
        var retrofit = Retrofit.Builder()
            .baseUrl("http://geoapi.heartrails.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        var api = retrofit.create(HeartRailsService::class.java)

        return api
    }
}