package com.fede.proyectogrupo02.restapi

import com.fede.proyectogrupo02.dto.GeocodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("search")
    fun searchCity(@Query("name") name: String): Call<GeocodingResponse>
}