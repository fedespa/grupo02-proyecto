package com.fede.proyectogrupo02.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityWeatherDto(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val windSpeed: Double
)
