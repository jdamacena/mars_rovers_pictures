package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.marpics.models.PhotoOfTheDayResponse
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch

class ApodViewModel : ViewModel() {

    private val photosRepository = PhotosRepository.getInstance()

    var photoOfTheDay = MutableLiveData<PhotoOfTheDayResponse>()

    fun queryApod() {
        viewModelScope.launch {
            photoOfTheDay = photosRepository?.queryApod()!!
        }
    }
}