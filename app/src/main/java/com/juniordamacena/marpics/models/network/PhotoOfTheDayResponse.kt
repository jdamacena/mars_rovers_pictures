package com.juniordamacena.marpics.models.network

data class PhotoOfTheDayResponse(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val thumbnail_url: String,
    val title: String,
    val url: String
)