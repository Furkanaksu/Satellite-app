package com.furkan.satellite_app.data.di

import com.furkan.satellite_app.domain.repository.SatelliteRepository
import com.furkan.satellite_app.data.repository.SatelliteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSatelliteRepository(repository: SatelliteRepositoryImpl): SatelliteRepository
}