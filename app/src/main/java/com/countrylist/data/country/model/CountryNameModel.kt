package com.countrylist.data.country.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryNameModel {

    @field:JsonProperty
    var common: String = "Ireland"

    @field:JsonProperty
    var official: String = "Republic of Ireland"

}
