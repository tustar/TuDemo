package com.tustar.demo.di

import com.tustar.demo.data.WeatherRepository
import com.tustar.demo.data.WeatherRepositoryImpl
import com.tustar.demo.data.remote.HeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHeService(): HeService {
        return HeService.create()
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(service: HeService): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}