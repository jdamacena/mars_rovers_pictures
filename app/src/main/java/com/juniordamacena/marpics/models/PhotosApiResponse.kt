package com.juniordamacena.marpics.models

import com.google.gson.annotations.SerializedName

data class PhotosApiResponse(
    @SerializedName(value = "photos", alternate = ["latest_photos"])
    val photos: List<Photo>
)