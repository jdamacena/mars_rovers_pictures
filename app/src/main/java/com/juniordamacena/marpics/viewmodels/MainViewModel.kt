package com.juniordamacena.marpics.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juniordamacena.marpics.models.main.Rover
import com.juniordamacena.marpics.repositories.PhotosRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val photosRepository: PhotosRepository by inject()

    var selectedTabIndex = 0

    private val _listRovers = MutableLiveData<List<Rover>>()
    private val _isLoading = MutableLiveData<Boolean>()

    fun getListRovers(): LiveData<List<Rover>> {
        return _listRovers
    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }

    fun queryRovers() {
        viewModelScope.launch {
            _isLoading.value = true

            _listRovers.value = photosRepository.queryRovers()

            _isLoading.value = false
        }
    }
}