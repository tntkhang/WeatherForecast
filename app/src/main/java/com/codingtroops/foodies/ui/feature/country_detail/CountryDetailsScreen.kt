package com.codingtroops.foodies.ui.feature.country_detail

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.codingtroops.foodies.R
import com.codingtroops.foodies.ui.feature.country_list.LoadingBar


@Composable
fun CountryDetailsScreen(navController: NavHostController, state: CountryDetailsContract.State) {
    Log.i("tntkhang", "=== NavController 2: ${navController}")
    Scaffold(
        topBar = {
            CityDetailAppBar(state) {
                navController.navigateUp()
            }
        }
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(Modifier.padding(16.dp)) {
                state.weatherForecast?.current?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    it.weatherIcons.firstOrNull()?.let {url ->
                        Row {
                            AsyncImage(
                                model = url,
                                contentDescription = it.weatherDescriptions.firstOrNull()
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                style = TextStyle(fontSize = 18.sp),
                                text = "${it.weatherDescriptions.firstOrNull()}")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        style = TextStyle(fontSize = 18.sp),
                        text = "${stringResource(id = R.string.temperature)} ${it.temperature}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        style = TextStyle(fontSize = 18.sp),
                        text = "${stringResource(id = R.string.feel_like)} ${it.feelslike}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        style = TextStyle(fontSize = 18.sp),
                        text = "${stringResource(id = R.string.humidity)} ${it.humidity}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        style = TextStyle(fontSize = 18.sp),
                        text = "${stringResource(id = R.string.uv_index)} ${it.uvIndex}")
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        style = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic),
                        text = "${stringResource(id = R.string.observationTime)} ${it.observationTime}")

                }
            }
            if (state.isLoading)
                LoadingBar()
        }
    }
}

@Composable
private fun CityDetailAppBar(state: CountryDetailsContract.State, onUpClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onUpClicked() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        },
        title = { state.weatherForecast?.location?.let { Text("${it.name}, ${it.country}", style = TextStyle(color = Color.White)) } },
        backgroundColor = MaterialTheme.colors.primary
    )
}