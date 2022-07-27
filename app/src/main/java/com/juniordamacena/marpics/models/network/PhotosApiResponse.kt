package com.juniordamacena.marpics.models.network

import com.google.gson.annotations.SerializedName
import com.juniordamacena.marpics.models.main.Photo

data class PhotosApiResponse(
    @SerializedName(value = "photos", alternate = ["latest_photos"])
    val photos: List<Photo>
)