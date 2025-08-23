package com.countrylist.data.country.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CountryImgModel {

    @field:JsonProperty
    var png: String = ""

    @field:JsonProperty
    var svg: String = ""

}
