package com.codingtroops.foodies.ui.feature.country_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingtroops.foodies.model.data.DataRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(private val remoteSource: DataRemoteSource) :
    ViewModel() {

    var state by mutableStateOf(
        CountryListContract.State(
            countries = listOf(),
            isLoading = true
        )
    )
        private set

    var effects = Channel<CountryListContract.Effect>(UNLIMITED)
        private set

    init {
        viewModelScope.launch { getCountryList() }
    }

    private suspend fun getCountryList() {
        val countries = remoteSource.getCountryList()
        viewModelScope.launch {
            state = state.copy(countries = countries, isLoading = false)
            effects.send(CountryListContract.Effect.DataWasLoaded)
        }
    }
}



