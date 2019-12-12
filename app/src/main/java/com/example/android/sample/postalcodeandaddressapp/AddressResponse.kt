package com.example.android.sample.postalcodeandaddressapp

data class Response(
    val location: Location
)

data class Location(
    val addresses: List<AddressResponse>
)

data class AddressResponse (

    val city : String,
    val city_kana: String,
    val town : String,
    val town_kana: String,
    val x: String,
    val y: String,
    val prefecture : String,
    val postal: String
)