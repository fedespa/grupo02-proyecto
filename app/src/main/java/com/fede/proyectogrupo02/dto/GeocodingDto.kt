package com.fede.proyectogrupo02.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodingResponse(
    val results: List<GeocodingResult>?
)

data class GeocodingResult(
    val id: Int?,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
    val country: String?,
    val admin1: String?
)