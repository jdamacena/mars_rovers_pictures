package com.juniordamacena.marpics.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.juniordamacena.marpics.models.main.Photo
import retrofit2.HttpException
import java.io.IOException

/*Created by junio on 30/07/2022.*/
class PhotosPagingSource(
    private val repository: PhotosRepository,
    private val roverName: String,
    private var sol: Int,
) : PagingSource<Int, Photo>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val listPhotos = repository.queryPhotosByRover(roverName, sol, nextPageNumber)
            return LoadResult.Page(
                data = listPhotos,
                prevKey = null, // Only paging forward.
                nextKey = if (sol == 0) {
                    null // Reached the end of all photos
                } else if (listPhotos.size < 25) { // Reached end of this day's photos
                    sol-- // Get images from the Sol before
                    1 // Restart Page Number
                } else {
                    nextPageNumber + 1
                }
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}