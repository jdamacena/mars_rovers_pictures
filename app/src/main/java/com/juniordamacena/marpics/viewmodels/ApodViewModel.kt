package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.marpics.models.PhotoOfTheDayResponse
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApodViewModel : ViewModel(), KoinComponent {

   private val photosRepository : PhotosRepository by inject()

    var photoOfTheDay = MutableLiveData<PhotoOfTheDayResponse>()

    fun queryApod() {
        viewModelScope.launch {
            photoOfTheDay.value = photosRepository?.queryApod()
        }
    }
}