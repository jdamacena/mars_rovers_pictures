package com.juniordamacena.marpics.repositories

import com.juniordamacena.marpics.models.Photo
import com.juniordamacena.marpics.models.PhotoOfTheDayResponse
import com.juniordamacena.marpics.models.Rover

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
    suspend fun queryPhotosByRover(rover: Rover): List<Photo>
}
