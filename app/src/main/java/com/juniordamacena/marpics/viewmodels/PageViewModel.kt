package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.*
import com.juniordamacena.marpics.repositories.PhotosRepositoryImpl
import kotlinx.coroutines.launch

class PageViewModel : ViewModel() {
    private val photosRepository = PhotosRepositoryImpl.getInstance()

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    var photoUrl = MutableLiveData<String>()

    fun queryPhotoUrl() {
        viewModelScope.launch {
           photoUrl.value = photosRepository?.queryPhotoUrl()
        }
    }
}