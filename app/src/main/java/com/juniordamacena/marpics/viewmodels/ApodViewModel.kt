package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.marpics.models.PhotoOfTheDayResponse
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApodViewModel : ViewModel(), KoinComponent {

    private val photosRepository: PhotosRepository by inject()

    private val _photoOfTheDay: MutableLiveData<PhotoOfTheDayResponse> =
        MutableLiveData<PhotoOfTheDayResponse>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun getPhotoOfTheDay(): LiveData<PhotoOfTheDayResponse> {
        return _photoOfTheDay
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }

    fun queryApod() {
        viewModelScope.launch {
            _isLoading.value = true

            _photoOfTheDay.value = photosRepository.queryApod()

            _isLoading.value = false
        }
    }
}