package com.codingtroops.foodies.model.data

import com.codingtroops.foodies.BuildConfig
import com.codingtroops.foodies.model.CountryItem
import com.codingtroops.foodies.model.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRemoteSource @Inject constructor(private val appApi: WeatherApi) {

    fun getCountryList(): List<CountryItem> {
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
        val countriesWithEmojis: ArrayList<CountryItem> = arrayListOf()
        for (countryCode in isoCountryCodes) {
            val locale = Locale("", countryCode)
            val countryName: String = locale.displayCountry
            val flagOffset = 0x1F1E6
            val asciiOffset = 0x41
            val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
            val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
            val flag =
                (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
            countriesWithEmojis.add(CountryItem(flag, countryName, countryCode))
        }
        return countriesWithEmojis
    }

    suspend fun getWeatherByCity(cityId: String): WeatherForecast = withContext(Dispatchers.IO) {
        return@withContext appApi.getWeatherforecast(BuildConfig.API_ACCESS_KEY, cityId)
    }

}