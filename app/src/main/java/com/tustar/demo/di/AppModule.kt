package com.tustar.demo.di

import com.tustar.demo.data.source.WeatherRepository
import com.tustar.demo.data.source.WeatherRepositoryImpl
import com.tustar.demo.data.source.remote.HeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(HeService.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHeService(retrofit: Retrofit): HeService {
        return retrofit.create(HeService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(service: HeService): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}