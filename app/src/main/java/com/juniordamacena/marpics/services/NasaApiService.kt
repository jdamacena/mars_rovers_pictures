package com.juniordamacena.marpics.services

import com.juniordamacena.marpics.models.PhotosApiResponse
import com.juniordamacena.marpics.models.RoversApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApiService {
    @GET("rovers")
    suspend fun listRovers(
        @Query("api_key") api_key: String
    ): Response<RoversApiResponse>

    @GET("rovers/{rover_name}/photos")
    suspend fun listPhotos(
        @Path("rover_name") rover_name: String,
        @Query("api_key") api_key: String,
        @Query("earth_date") earth_date: String?,
        @Query("sol") sol: Int?,
        @Query("page") page: Int = 1,
        @Query("camera") camera: String? = null,
    ): Response<PhotosApiResponse>

    /**
     * Get the Astronomy Photo of the Day (APOD)
     */
    @GET("/planetary/apod")
    suspend fun getAPOD(
        @Query("api_key") api_key: String
    ): Response<RoversApiResponse>

}