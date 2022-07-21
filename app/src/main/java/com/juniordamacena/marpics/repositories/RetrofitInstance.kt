package com.juniordamacena.marpics.repositories

import com.juniordamacena.marpics.services.NasaApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val API_KEY = "eLW4swao7pg6L1IFzeVbjlIoCo1HE9xlyZSUPVU1"

    val api: NasaApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }
}