package com.codingtroops.foodies.model.data

import com.codingtroops.foodies.BuildConfig
import com.codingtroops.foodies.model.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherApi @Inject constructor(private val service: Service) {

    suspend fun getWeatherforecast(accessKey: String, countryId: String): WeatherForecast =
        service.getWeatherforecast(accessKey, countryId)

    interface Service {
        //        http://api.weatherstack.com/current
//        ? access_key = YOUR_ACCESS_KEY
//        & query = New York
        @GET("current")
        suspend fun getWeatherforecast(
            @Query("access_key") accessKey: String,
            @Query("query") query: String
        ): WeatherForecast
    }

    companion object {
        const val API_URL = BuildConfig.BASE_URL
    }
}


