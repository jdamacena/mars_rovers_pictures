package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.juniordamacena.marpics.models.main.Rover
import com.juniordamacena.marpics.repositories.PhotosPagingSource
import com.juniordamacena.marpics.repositories.PhotosRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PageViewModel(val rover: Rover) : ViewModel(), KoinComponent {
    private val photosRepository: PhotosRepository by inject()

    private var _photosPagingSource: PhotosPagingSource? = null

    private fun pagingSourceFactory(): PhotosPagingSource {
        _photosPagingSource = PhotosPagingSource(photosRepository, rover.name, rover.max_sol!!)
        return _photosPagingSource!!
    }

    // Configure how data is loaded by passing additional properties to
    // PagingConfig, such as prefetchDistance.
    val flow = Pager(PagingConfig(pageSize = 25)) {
        pagingSourceFactory()
    }
        .flow
        .cachedIn(viewModelScope)

    /**
     * Invalidates the [PhotosPagingSource] so that it can start loading again, refreshing its data
     */
    fun invalidatePagingSource() {
        _photosPagingSource?.invalidate()
    }
}