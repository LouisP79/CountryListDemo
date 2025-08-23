package com.countrylist.data.country

import com.countrylist.data.RestConstant
import com.countrylist.data.country.model.CountryDetailModel
import com.countrylist.data.country.model.CountryModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val FIELDS = "fields"
const val FULL_TEXT = "fullText"
interface CountryWebServices {

    @GET(RestConstant.ENDPOINT_ALL_COUNTRY_LIST)
    suspend fun getAllCountryList(@Query(FIELDS) fields: String = "name,capital,flags"):
            Response<List<CountryModel>>

    @GET(RestConstant.ENDPOINT_COUNTRY_BY_NAME + "/{search}")
    suspend fun searchCountryList(@Path("search") search: String,
                                  @Query(FIELDS) fields: String = "name,capital,flags"):
            Response<List<CountryModel>>

    @GET(RestConstant.ENDPOINT_COUNTRY_BY_NAME + "/{countryName}")
    suspend fun getCountryDetail(@Path("countryName") countryName: String,
                                 @Query(FULL_TEXT) fullText: Boolean = true):
            Response<List<CountryDetailModel>>
}
