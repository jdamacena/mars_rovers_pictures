package com.juniordamacena.marpics.repositories

import android.util.Log
import com.juniordamacena.marpics.models.Photo
import com.juniordamacena.marpics.models.PhotoOfTheDayResponse
import com.juniordamacena.marpics.models.Rover
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 20/07/2022.*/
class PhotosRepositoryImpl : PhotosRepository {
    override suspend fun queryPhotoUrl(): String {
        var photoUrl = ""

        try {
            val response = RetrofitInstance.api.listPhotos(
                "Curiosity",
                RetrofitInstance.API_KEY,
                sol = (10..100).random(),
                earth_date = null
            )
            val listPhotos: List<Photo>? = response.body()?.photos
            val firstPhoto = listPhotos?.firstOrNull()

            photoUrl = firstPhoto?.img_src
                ?: "https://www.iconsdb.com/icons/download/red/error-7-128.jpg"

        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }


        return photoUrl
    }

    override suspend fun queryApod(): PhotoOfTheDayResponse? {
        var photo : PhotoOfTheDayResponse? = null

        try {
            val response = RetrofitInstance.api.getAPOD(RetrofitInstance.API_KEY)
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
            val response = RetrofitInstance.api.listRovers(RetrofitInstance.API_KEY)
            val listRovers: List<Rover> = response.body()?.rovers ?: emptyList()

            rovers = listRovers
        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }

        return rovers
    }

    companion object {
        private var instance: PhotosRepositoryImpl? = null

        fun getInstance(): PhotosRepositoryImpl? {
            if (instance == null) {
                instance = PhotosRepositoryImpl()
            }
            return instance
        }
    }

}