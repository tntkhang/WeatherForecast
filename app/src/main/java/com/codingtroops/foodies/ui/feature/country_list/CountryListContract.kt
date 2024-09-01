package com.codingtroops.foodies.ui.feature.country_list

import com.codingtroops.foodies.model.CountryItem


class CountryListContract {

    data class State(
        val countries: List<CountryItem> = listOf(),
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}