package com.countrylist.ui.navigation

import androidx.compose.ui.res.stringResource
import com.countrylist.R

const val HOME = "home"
const val BUTTON = "BUTTON"
const val FAVORITES = "favorites"
const val COUNTRY_NAME = "countryName"
const val DETAIL = "detail/{$COUNTRY_NAME}"

sealed class NavigationRouter(
    val route: String,
    val icon: Int,
    val title: Int
) {
    object Home : NavigationRouter(HOME, R.drawable.ic_earth, R.string.home)
    object Favorites : NavigationRouter(FAVORITES, R.drawable.ic_favorite_on, R.string.favorites)
    object Detail : NavigationRouter(DETAIL, 0, 0) {
        fun createRoute(countryName: String): String {
           return this.route.replace("{$COUNTRY_NAME}", countryName)
        }
    }
}
