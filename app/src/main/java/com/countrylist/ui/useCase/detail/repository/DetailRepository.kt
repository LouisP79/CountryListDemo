package com.countrylist.ui.useCase.detail.repository

import com.countrylist.data.country.CountryWebServices
import com.countrylist.data.country.model.CountryDetailModel
import com.countrylist.util.repository.RepoResponse

class DetailRepository(private val countryWebServices: CountryWebServices) {

    suspend fun getDetailCountry(countryName: String): RepoResponse<List<CountryDetailModel>>{
        return try {
            val response = countryWebServices.getCountryDetail(countryName)
            RepoResponse.respond(response)
        } catch (e: Exception) {
            RepoResponse.respond(throwable = e)
        }
    }
}