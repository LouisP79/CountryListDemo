package com.countrylist.ui.useCase.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countrylist.R
import com.countrylist.ui.useCase.home.repository.HomeRepository
import com.countrylist.ui.useCase.home.uIState.HomeUIState
import com.countrylist.util.core.errorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel(){

    private val homeUIState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    fun getHomeUIState() = homeUIState.asStateFlow()

    fun resetView(){
        getCountries()
    }

    fun getCountries(){
        homeUIState.value = HomeUIState.Loading
        viewModelScope.launch {
            val response = homeRepository.getAllCountryList()
            response.dataResponse?.let { response ->
                if (response.isSuccessful) {
                    homeUIState.value = HomeUIState.Success(
                        countries = response.body() ?: listOf(),
                        "",
                        isLoadingSearching = false
                    )
                }else{
                    homeUIState.value = HomeUIState.Error(
                        errorCode = errorCode(response.code())
                    )
                }
            } ?: response.throwable?.let {
                homeUIState.value = HomeUIState.Error(
                    message = it.message ?: ""
                )
            }
        }
    }

    fun searchCountries(search: String){
        val currentState = homeUIState.value
        if (currentState is HomeUIState.Success) {
            homeUIState.value = currentState.copy(isLoadingSearching = true)
        }
        viewModelScope.launch {
            val response = homeRepository.searchCountryList(search)
            response.dataResponse?.let { response ->
                if (response.isSuccessful) {
                    homeUIState.value = HomeUIState.Success(
                        countries = response.body() ?: listOf(),
                        search,
                        isLoadingSearching = false
                    )
                }else{
                    if(errorCode(response.code()) != R.string.not_found_error) {
                        homeUIState.value = HomeUIState.Error(
                            errorCode = errorCode(response.code())
                        )
                    }
                }
            } ?: response.throwable?.let {
                homeUIState.value = HomeUIState.Error(
                    message = it.message ?: ""
                )
            }
        }
    }

}