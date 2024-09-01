package com.codingtroops.foodies.di

import com.codingtroops.foodies.model.data.WeatherApi
import com.codingtroops.foodies.model.data.WeatherApi.Companion.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModules {

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodMenuApiService(
        retrofit: Retrofit
    ): WeatherApi.Service {
        return retrofit.create(WeatherApi.Service::class.java)
    }
//
//    @Provides
//    @Singleton
//    fun providesRealmConfigs(): Realm {
//        val config = RealmConfiguration.create(setOf(Note::class))
//        return Realm.open(config)
//    }

}