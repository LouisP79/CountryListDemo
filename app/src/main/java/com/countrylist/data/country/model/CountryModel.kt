package com.countrylist.data.country.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryModel(
    @field:JsonProperty var name: CountryNameModel = CountryNameModel(),
    @field:JsonProperty var capital: List<String> = listOf("Dublin"),
    @field:JsonProperty var flags: CountryImgModel = CountryImgModel()
) {

    fun getCapitals(): String {
        var capitalResult = ""
        capital.forEach { capital ->
            capitalResult += "$capital, "
        }
        if (capitalResult.length > 2)
            return capitalResult.substring(0, capitalResult.length - 2)
        return capitalResult

    }

}
