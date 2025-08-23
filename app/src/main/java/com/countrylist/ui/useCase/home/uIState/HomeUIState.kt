package com.countrylist.ui.useCase.home.uIState

import com.countrylist.data.country.model.CountryModel

sealed class HomeUIState {
    data object Loading : HomeUIState()
    data class Success(
        val countries: List<CountryModel>,
        val searchingValue: String = "",
        val isLoadingSearching: Boolean = false
    ) : HomeUIState()

    data class Error(
        val message: String = "",
        val errorCode: Int? = null
    ) : HomeUIState()
}
