package com.juniordamacena.marpics.di

import com.juniordamacena.marpics.repositories.PhotosRepository
import com.juniordamacena.marpics.repositories.PhotosRepositoryImpl
import com.juniordamacena.marpics.services.NasaApiService
import com.juniordamacena.marpics.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*Created by junio on 24/07/2022.*/
val appModule = module {

    single(named("API_KEY")) { "eLW4swao7pg6L1IFzeVbjlIoCo1HE9xlyZSUPVU1" }

    single<NasaApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }

    single<PhotosRepository> { PhotosRepositoryImpl(get(), get(named("API_KEY"))) }

    viewModel { ApodViewModel() }
    viewModel { PageViewModel() } //{ parameters -> PageViewModel(rover = parameters.get()) }
    viewModel { MainViewModel() }
    viewModel { GalleryViewModel() }
    viewModel { GalleryPageViewModel() }
}