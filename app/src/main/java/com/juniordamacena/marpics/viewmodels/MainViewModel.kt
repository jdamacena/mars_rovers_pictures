package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val photosRepository: PhotosRepository by inject()

    var selectedTabIndex = 0
    private var _listRovers = MutableLiveData<List<Rover>>()

    fun getListRovers(): LiveData<List<Rover>> {
        return _listRovers
    }

    private var _isLoading = MutableLiveData<Boolean>()

    fun getIsLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }

    fun queryRovers() {
        viewModelScope.launch {
            _isLoading.value = true

            _listRovers.value = photosRepository?.queryRovers()

            _isLoading.value = false
        }
    }
}