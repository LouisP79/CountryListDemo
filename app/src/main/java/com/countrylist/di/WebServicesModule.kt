package com.countrylist.di

import com.countrylist.data.country.CountryWebServices
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val webServiceModule = module {
    singleOf(::provideCountryServices)
}

fun provideCountryServices(retrofit: Retrofit): CountryWebServices = retrofit.create(CountryWebServices::class.java)