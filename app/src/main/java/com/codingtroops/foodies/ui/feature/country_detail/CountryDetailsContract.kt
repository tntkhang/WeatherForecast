package com.codingtroops.foodies.ui.feature.country_detail

import com.codingtroops.foodies.model.WeatherForecast

class CountryDetailsContract {
    data class State(
        val weatherForecast: WeatherForecast?,
        val isLoading: Boolean = true
    )
}