package com.juniordamacena.marpics.models

data class PhotoOfTheDayResponse(
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)