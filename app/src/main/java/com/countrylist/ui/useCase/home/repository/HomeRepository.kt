package com.countrylist.ui.useCase.home.repository

import com.countrylist.data.country.CountryWebServices
import com.countrylist.data.country.model.CountryModel
import com.countrylist.util.repository.RepoResponse

class HomeRepository(private val countryWebServices: CountryWebServices) {

    suspend fun getAllCountryList(): RepoResponse<List<CountryModel>>{
        return try {
            val response = countryWebServices.getAllCountryList()
            RepoResponse.respond(response)
        } catch (e: Exception) {
            RepoResponse.respond(throwable = e)
        }
    }

    suspend fun searchCountryList(search: String): RepoResponse<List<CountryModel>>{
        return try {
            val response = countryWebServices.searchCountryList(search)
            RepoResponse.respond(dataResponse = response)
        } catch (e: Exception) {
            RepoResponse.respond(throwable = e)
        }
    }
}