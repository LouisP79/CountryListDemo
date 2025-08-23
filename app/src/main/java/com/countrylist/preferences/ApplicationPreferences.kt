package com.countrylist.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.countrylist.data.country.model.CountryModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

private const val NAME = "appPreference"
private const val KEY_FAVORITES_COUNTRIES = "favorites_countries"

class ApplicationPreferences(context: Context, private val gson: Gson) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var favoritesCountries: List<CountryModel>
        get() = gson.fromJson(sharedPreferences.getString(KEY_FAVORITES_COUNTRIES, java.lang.String.valueOf(ArrayList<CountryModel>())), object : TypeToken<List<CountryModel>>() {}.type)
        set(value){
            val type: Type = object : TypeToken<List<CountryModel?>?>() {}.type
            sharedPreferences.edit{putString(KEY_FAVORITES_COUNTRIES, gson.toJson(value, type))}
        }
}