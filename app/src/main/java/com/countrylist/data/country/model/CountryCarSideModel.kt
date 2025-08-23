package com.countrylist.data.country.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryCarSideModel {

    @field:JsonProperty
    var side = ""
}
