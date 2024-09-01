package com.codingtroops.foodies.ui.feature.country_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.codingtroops.foodies.model.data.DataRemoteSource
import com.codingtroops.foodies.ui.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val repository: DataRemoteSource
) : ViewModel() {

    var state by mutableStateOf(
        CountryDetailsContract.State(
            null, true
        )
    )
        private set

    init {
        viewModelScope.launch {
            val cityId = stateHandle.get<String>(NavigationKeys.Arg.COUNTRY_ID)
                ?: throw IllegalStateException("No categoryId was passed to destination.")
            val weatherForecast = repository.getWeatherByCity(cityId)
            state = state.copy(weatherForecast = weatherForecast, isLoading = false)
        }
    }

}
