package com.juniordamacena.marpics.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.juniordamacena.marpics.models.Photo
import com.juniordamacena.marpics.models.PhotoOfTheDayResponse
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 20/07/2022.*/
class PhotosRepository {

    suspend fun queryPhotoUrl(): MutableLiveData<String> {
        val photoUrl: MutableLiveData<String> = MutableLiveData<String>()

        try {
            val response = RetrofitInstance.api.listPhotos(
                "Curiosity",
                RetrofitInstance.API_KEY,
                sol = (10..100).random(),
                earth_date = null
            )
            val listPhotos: List<Photo>? = response.body()?.photos
            val firstPhoto = listPhotos?.firstOrNull()

            photoUrl.setValue(
                firstPhoto?.img_src
                    ?: "https://www.iconsdb.com/icons/download/red/error-7-128.jpg"
            )

        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }


        return photoUrl
    }

    suspend fun queryApod(): MutableLiveData<PhotoOfTheDayResponse> {
        val photoUrl = MutableLiveData<PhotoOfTheDayResponse>()

        try {
            val response = RetrofitInstance.api.getAPOD(RetrofitInstance.API_KEY)
            val apod: PhotoOfTheDayResponse? = response.body()

            photoUrl.value = apod!!
        } catch (e: IOException) {
            Log.e("PhotosRepository", e.message ?: "IOException")
        } catch (e: HttpException) {
            Log.e("PhotosRepository", e.message ?: "HttpException")
        }

        return photoUrl
    }

    companion object {
        private var instance: PhotosRepository? = null

        fun getInstance(): PhotosRepository? {
            if (instance == null) {
                instance = PhotosRepository()
            }
            return instance
        }
    }

}