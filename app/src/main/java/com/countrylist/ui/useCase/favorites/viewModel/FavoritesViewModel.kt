package com.countrylist.ui.useCase.favorites.viewModel

import androidx.lifecycle.ViewModel
import com.countrylist.preferences.ApplicationPreferences
import com.countrylist.ui.useCase.favorites.uIState.FavoritesUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModel(private val applicationPreferences: ApplicationPreferences): ViewModel(){

    private val favoritesUIState = MutableStateFlow<FavoritesUIState>(FavoritesUIState.Loading)
    fun getFavoritesUIState() = favoritesUIState.asStateFlow()

    fun getFavoritesCountries(){
       favoritesUIState.value = FavoritesUIState.Success(countries = applicationPreferences.favoritesCountries)
    }

}