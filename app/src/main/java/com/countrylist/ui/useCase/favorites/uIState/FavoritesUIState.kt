package com.countrylist.ui.useCase.favorites.uIState

import com.countrylist.data.country.model.CountryModel

sealed class FavoritesUIState {
    data object Loading : FavoritesUIState()
    data class Success(
        val countries: List<CountryModel>
    ) : FavoritesUIState()
}