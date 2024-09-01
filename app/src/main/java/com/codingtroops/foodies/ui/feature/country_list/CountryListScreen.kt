package com.codingtroops.foodies.ui.feature.country_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.codingtroops.foodies.R
import com.codingtroops.foodies.model.CountryItem
import com.codingtroops.foodies.ui.theme.ComposeSampleTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@ExperimentalCoilApi
@Composable
fun CountryListScreen(
    state: CountryListContract.State,
    effectFlow: Flow<CountryListContract.Effect>?,
    onNavigationRequested: (item: CountryItem) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val searchText = rememberSaveable { mutableStateOf("") }
    val countries = remember { mutableStateOf(state.countries) }
    var filteredCountries: ArrayList<CountryItem>

    // Listen for side effects from the VM
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is CountryListContract.Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Food categories are loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WeatherAppBar()
        },
    ) {
        Box {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchText.value,
                    onValueChange = {
                        searchText.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        ),
                    label = { Text(stringResource(R.string.search_city))},
                    trailingIcon = {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "clear text",
                            modifier = Modifier
                                .clickable {
                                    searchText.value = ""
                                }
                        )
                    }
                )
                LazyColumn(Modifier.padding(top = 8.dp)) {
                    filteredCountries = if (searchText.value.isEmpty()) {
                        ArrayList(countries.value)
                    } else {
                        val resultList = ArrayList<CountryItem>()
                        for (country in countries.value) {
                            if (country.name.lowercase(Locale.getDefault())
                                    .contains(searchText.value.lowercase(Locale.getDefault()))
                            ) {
                                resultList.add(country)
                            }
                        }
                        resultList
                    }
                    items(filteredCountries, itemContent = { item ->
                        CountryItemRow(item = item) { item ->
                            onNavigationRequested(item)
                        }
                    })
                }
            }
            if (state.isLoading)
                LoadingBar()
        }
    }

}

@Composable
private fun WeatherAppBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name), style = TextStyle(color = Color.White)) },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun CountryItemRow(
    item: CountryItem,
    onItemClicked: (id: CountryItem) -> Unit = { }
) {
    Box(
        modifier = Modifier
            .padding(all = 12.dp)
            .clickable { onItemClicked(item) }
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                Text(
                    text = item.flag
                )
            }
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(start = 8.dp) // margin
            )

            Text(
                text = "(${item.code})",
                modifier = Modifier
                    .padding(start = 8.dp) // mar
            )

        }
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        CountryListScreen(CountryListContract.State(), null, { })
    }
}