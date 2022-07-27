package com.juniordamacena.marpics.repositories

import android.util.Log
import com.juniordamacena.marpics.models.main.Photo
import com.juniordamacena.marpics.models.network.PhotoOfTheDayResponse
import com.juniordamacena.marpics.models.main.Rover
import com.juniordamacena.marpics.services.NasaApiService
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 20/07/2022.*/
class PhotosRepositoryImpl(
    private val nasaApiService: NasaApiService,
    private val API_KEY: String
) : PhotosRepository {

    override suspend fun queryApod(): PhotoOfTheDayResponse? {
        var photo: PhotoOfTheDayResponse? = null

        try {
            val response = nasaApiService.getAPOD(API_KEY)
            val apod: PhotoOfTheDayResponse? = response.body()

            photo = apod!!
        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }

        return photo
    }

    override suspend fun queryRovers(): List<Rover> {
        var rovers: List<Rover> = emptyList()

        try {
            val response = nasaApiService.listRovers(API_KEY)
            val listRovers: List<Rover> = response.body()?.rovers ?: emptyList()

            rovers = listRovers
        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }

        return rovers
    }

    override suspend fun queryLatestPhotosByRover(roverName: String): List<Photo> {
        var photosList: List<Photo> = emptyList()

        try {
            val response = nasaApiService.listLatestPhotos(roverName, API_KEY)
            val listPhotos: List<Photo>? = response.body()?.photos

            Log.d("response", response.toString())

            photosList = listPhotos ?: emptyList()

        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }

        return photosList
    }

    override suspend fun queryPhotosByRover(rover: Rover): List<Photo> {
        var photosList: List<Photo> = emptyList()

        try {
            val response = nasaApiService.listPhotos(
                rover.name,
                API_KEY,
                earth_date = rover.max_date ?: rover.landing_date,
                sol = null
            )
            val listPhotos: List<Photo>? = response.body()?.photos

            photosList = listPhotos ?: emptyList()

        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }

        return photosList
    }
}