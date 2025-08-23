package com.countrylist.ui.useCase.detail.uIState

import com.countrylist.data.country.model.CountryDetailModel

sealed class DetailUIState {
    data object Loading : DetailUIState()
    data class Success(
        val detailCountry: CountryDetailModel,
        val countryName: String = "",
    ) : DetailUIState()
    data class Error(
        val message: String = "",
        val errorCode: Int? = null
    ) : DetailUIState()
}