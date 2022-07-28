package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.marpics.models.main.Photo
import com.juniordamacena.marpics.models.main.Rover
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PageViewModel() : ViewModel(), KoinComponent {
    private val photosRepository: PhotosRepository by inject()

    var selectedId = 0

    private val _isLoading = MutableLiveData<Boolean>()
    private val _photos = MutableLiveData<List<Photo>>()

    fun getIsLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }

    fun getPhotos(): LiveData<List<Photo>> {
        return _photos
    }

    fun queryPhotos(roverName: String) {
        viewModelScope.launch {
            _isLoading.value = true

//            _photos.value = photosRepository.queryPhotosByRover(rover)
            _photos.value = photosRepository.queryLatestPhotosByRover(roverName)

            _isLoading.value = false
        }
    }
}