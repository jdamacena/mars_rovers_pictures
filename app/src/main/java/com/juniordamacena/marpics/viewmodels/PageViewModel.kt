package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.*
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PageViewModel : ViewModel(), KoinComponent {
    private val photosRepository: PhotosRepository by inject()

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    private val _photoUrl = MutableLiveData<String>()

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }

    fun getPhotoUrl(): LiveData<String> {
        return _photoUrl
    }

    fun queryPhotoUrl() {
        viewModelScope.launch {
            _isLoading.value = true

            _photoUrl.value = photosRepository.queryPhotoUrl()

            _isLoading.value = false
        }
    }
}