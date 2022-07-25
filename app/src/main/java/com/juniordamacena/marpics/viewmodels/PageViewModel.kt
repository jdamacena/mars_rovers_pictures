package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.*
import com.juniordamacena.marpics.models.Photo
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PageViewModel(private val rover: Rover) : ViewModel(), KoinComponent {
    private val photosRepository: PhotosRepository by inject()

    private val _isLoading = MutableLiveData<Boolean>()
    private val _photos = MutableLiveData<List<Photo>>()

    fun getIsLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }

    fun getPhotos(): LiveData<List<Photo>> {
        return _photos
    }

    fun queryPhotos() {
        viewModelScope.launch {
            _isLoading.value = true

            _photos.value = photosRepository.queryPhotosByRover(rover)

            _isLoading.value = false
        }
    }
}