package com.example.android.sample.postalcodeandaddressapp

data class AddressResponse (

    val city : String,
    val city_kana: String,
    val town : String,
    val town_kana: String,
    val latitude: String,
    val longitude: String,
    val prefecture : String,
    val postal: String
)