package com.countrylist.ui.navigation


import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.countrylist.ui.useCase.detail.DetailScreen
import com.countrylist.ui.useCase.favorites.FavoritesScreen
import com.countrylist.ui.useCase.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController){
    var selectedItem by remember { mutableIntStateOf(0) }
    var showBottomBar by remember { mutableStateOf(true) }
    val items = listOf(NavigationRouter.Home, NavigationRouter.Favorites)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Image(
                                    painter = painterResource(item.icon),
                                    contentDescription = stringResource(item.title)
                                )
                            },
                            label = { Text(stringResource(item.title)) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding  ->
        NavHost(navController = navController, startDestination = NavigationRouter.Home.route){
            composable(route = NavigationRouter.Home.route) {
                showBottomBar = true
                HomeScreen( innerPadding = innerPadding,
                    navigateToDetail = { countryName ->
                    navController.navigate(
                        route = NavigationRouter.Detail.createRoute(countryName)
                    )
                })
            }
            composable(route = NavigationRouter.Favorites.route) {
                showBottomBar = true
                FavoritesScreen(innerPadding = innerPadding, navigateToDetail = { countryName ->
                    navController.navigate(
                        route = NavigationRouter.Detail.createRoute(countryName)
                    )
                })
            }
            composable(
                route = NavigationRouter.Detail.route,
                arguments = listOf(
                    navArgument(COUNTRY_NAME) {
                        defaultValue = ""
                        type = NavType.StringType
                    }
                )
            ){
                showBottomBar = false
                val countryName = it.arguments?.getString(COUNTRY_NAME) ?: ""
                DetailScreen(countryName,
                    onBack = {
                        navController.popBackStack()
                        navController.navigate(HOME)
                    })
            }
        }

    }

}