package com.practice.univapp.di

import com.practice.univapp.network.api.UniversityService
import com.practice.univapp.constant.UniversityConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideApiService(): UniversityService {
        return Retrofit.Builder().baseUrl(UniversityConstant.BASEURL.UNIVERSITY_BASEURL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(UniversityService::class.java)
    }
}