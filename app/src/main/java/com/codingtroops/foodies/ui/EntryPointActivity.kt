package com.codingtroops.foodies.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codingtroops.foodies.ui.NavigationKeys.Arg.COUNTRY_ID
import com.codingtroops.foodies.ui.NavigationKeys.Arg.FOOD_CATEGORY_ID
import com.codingtroops.foodies.ui.feature.country_detail.CountryDetailsScreen
import com.codingtroops.foodies.ui.feature.country_detail.CountryDetailsViewModel
import com.codingtroops.foodies.ui.feature.country_list.CountryListScreen
import com.codingtroops.foodies.ui.feature.country_list.CountryListViewModel
import com.codingtroops.foodies.ui.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow


// Single Activity per app
@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                FoodApp()
            }
        }
    }

}

@Composable
private fun FoodApp() {
    val navController = rememberNavController()
    Log.i("tntkhang", "=== NavController 1: ${navController}")
    NavHost(navController, startDestination = NavigationKeys.Route.COUNTRY_LIST) {
        composable(route = NavigationKeys.Route.COUNTRY_LIST) {
            CountryListDestination(navController)
        }
        composable(
            route = NavigationKeys.Route.COUNTRY_LIST_DETAILS,
            arguments = listOf(navArgument(COUNTRY_ID) {
                type = NavType.StringType
            })
        ) {
            CountryDetailsDestination(navController)
        }
    }
}

@Composable
private fun CountryListDestination(navController: NavHostController) {
    val viewModel: CountryListViewModel = hiltViewModel()
    CountryListScreen(
        state = viewModel.state,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = { item ->
            navController.navigate("${NavigationKeys.Route.COUNTRY_LIST}/${item.name}")
        })
}

@Composable
private fun CountryDetailsDestination(navController: NavHostController) {
    val viewModel: CountryDetailsViewModel = hiltViewModel()
    CountryDetailsScreen(navController, viewModel.state)
}

object NavigationKeys {

    object Arg {
        const val FOOD_CATEGORY_ID = "foodCategoryName"
        const val COUNTRY_ID = "countryId"
    }

    object Route {
        const val COUNTRY_LIST = "country_list"
        const val COUNTRY_LIST_DETAILS = "$COUNTRY_LIST/{$COUNTRY_ID}"
        const val FOOD_CATEGORIES_LIST = "food_categories_list"
        const val FOOD_CATEGORY_DETAILS = "$FOOD_CATEGORIES_LIST/{$FOOD_CATEGORY_ID}"
    }

}