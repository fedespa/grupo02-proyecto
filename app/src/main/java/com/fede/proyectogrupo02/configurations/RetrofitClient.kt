package com.fede.proyectogrupo02.configurations

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofitGeo = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitWeather = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

}