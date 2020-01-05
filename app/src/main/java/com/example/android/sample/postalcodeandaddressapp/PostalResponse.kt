package com.example.android.sample.postalcodeandaddressapp

data class PostalResponse(
    val response: Response
)

data class Response(
    val location: List<Location>
)

data class Location (

    val city : String,
    val city_kana: String,
    val town : String,
    val town_kana: String,
    val x: String,
    val y: String,
    val prefecture : String,
    val postal: String
)