package com.fede.proyectogrupo02.restapi

import com.fede.proyectogrupo02.dto.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") current: Boolean = true
    ): Call<WeatherResponse>
}