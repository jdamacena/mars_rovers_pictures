package com.juniordamacena.marpics.repositories

import com.juniordamacena.marpics.models.main.Photo
import com.juniordamacena.marpics.models.network.PhotoOfTheDayResponse
import com.juniordamacena.marpics.models.main.Rover

interface PhotosRepository {
    /**
     * Fetches the Astronomy Photo of the Day data
     */
    suspend fun queryApod(): PhotoOfTheDayResponse?

    /**
     * Fetches a list of Rovers objects
     */
    suspend fun queryRovers(): List<Rover>

    /**
     * Fetches a list of photos from a rover
     */
    suspend fun queryLatestPhotosByRover(roverName: String): List<Photo>

    suspend fun queryPhotosByRover(rover: Rover): List<Photo>
}
