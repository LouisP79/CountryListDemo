package com.countrylist.ui.useCase.detail.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countrylist.data.country.model.CountryModel
import com.countrylist.preferences.ApplicationPreferences
import com.countrylist.ui.useCase.detail.repository.DetailRepository
import com.countrylist.ui.useCase.detail.uIState.DetailUIState
import com.countrylist.util.core.errorCode
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository,
                      private val applicationPreferences: ApplicationPreferences): ViewModel(){

    private val detailUIState = MutableStateFlow<DetailUIState>(DetailUIState.Loading)
    fun getDetailUIState() = detailUIState.asStateFlow()

    @SuppressLint("HardwareIds")
    fun saveDeleteCountry(country: CountryModel, context: Context){
        val aux = applicationPreferences.favoritesCountries
        if(isCountryFavorite(country.name.common)){
            applicationPreferences.favoritesCountries = aux.filter { it.name.common != country.name.common }
        }else{
            applicationPreferences.favoritesCountries = aux + country
        }

        val androidId: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("DEVICE ID", androidId)

        FirebaseDatabase.getInstance().getReference().child(androidId).setValue(applicationPreferences.favoritesCountries)
    }

    fun isCountryFavorite(common: String): Boolean{
        val aux = applicationPreferences.favoritesCountries
        aux.forEach { country ->
            if(country.name.common == common){
                return true
            }
        }
        return false
    }

    fun getCountries(countryName: String){
        detailUIState.value = DetailUIState.Loading
        viewModelScope.launch {
            val response = detailRepository.getDetailCountry(countryName)
            response.dataResponse?.let {
                if (response.dataResponse.isSuccessful) {
                    response.dataResponse.body()?.let { data ->
                        detailUIState.value = DetailUIState.Success(
                            detailCountry = data[0],
                            countryName = countryName
                        )
                    }
                } else {
                    detailUIState.value = DetailUIState.Error(
                        errorCode = errorCode(response.dataResponse.code())
                    )
                }
            }?: response.throwable?.let {
                detailUIState.value = DetailUIState.Error(
                    message = it.message ?: ""
                )
            }
        }
    }

}