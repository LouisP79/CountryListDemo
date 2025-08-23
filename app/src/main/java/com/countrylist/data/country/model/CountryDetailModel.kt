package com.countrylist.data.country.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryDetailModel {

    @field:JsonProperty
    var name = CountryNameModel()

    @field:JsonProperty
    var capital = listOf("Dublin")

    @field:JsonProperty
    var flags = CountryImgModel()

    @field:JsonProperty
    var coatOfArms = CountryImgModel()

    @field:JsonProperty
    var region = ""

    @field:JsonProperty
    var subregion = ""

    @field:JsonProperty
    var area: Long = 0

    @field:JsonProperty
    var population: Long = 0

    @field:JsonProperty
    var languages = mapOf<String, String>()

    @field:JsonProperty
    var car = CountryCarSideModel()

    @field:JsonProperty
    var currencies = mapOf<String, CountryCurencyModel>()

    @field:JsonProperty
    var timezones = listOf<String>()

    fun getCapitals(): String {
        var capitalResult = ""
        capital.forEach { capital ->
            capitalResult += "$capital, "
        }
        if(capitalResult.length > 2)
            return capitalResult.substring(0, capitalResult.length - 2)
        return capitalResult

    }

    fun getLanguages(): String {
        var languageResult = ""
        languages.forEach { (_, value) ->
            languageResult += "$value\n"
        }
        if(languageResult.length > 2)
            return languageResult.substring(0, languageResult.length - 2)
        return languageResult
    }

    fun getCurrencies(): String {
        var currencyResult = ""
        currencies.forEach { (_, value) ->
            currencyResult += "${value.name}(${value.symbol}) \n"
        }
        if(currencyResult.length > 2)
            return currencyResult.substring(0, currencyResult.length - 2)
        return currencyResult
    }

    fun getTimeZones(): String {
        var timezoneResult = ""
        timezones.forEach { timezone ->
            timezoneResult += "$timezone\n"
        }
        if(timezoneResult.length > 2)
            return timezoneResult.substring(0, timezoneResult.length - 2)
        return timezoneResult

    }

}
