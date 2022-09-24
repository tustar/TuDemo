package com.tustar.data.di

import com.tustar.data.source.WeatherRepository
import com.tustar.data.source.WeatherRepositoryImpl
import com.tustar.data.source.remote.BaseUrlInterceptor
import com.tustar.data.source.remote.HeService
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
object DataModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    fun provideBaseUrlInterceptor() = BaseUrlInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor,
        baseUrl: BaseUrlInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(HeService.HE_BASE_URL)
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