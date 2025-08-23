package com.countrylist.di

import com.countrylist.data.country.CountryWebServices
import com.countrylist.ui.useCase.detail.repository.DetailRepository
import com.countrylist.ui.useCase.home.repository.HomeRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::provideHomeRepository)
    factoryOf(::provideDetailRepository)

}

fun provideHomeRepository(countryWebServices: CountryWebServices): HomeRepository =
    HomeRepository(countryWebServices)

fun provideDetailRepository(countryWebServices: CountryWebServices): DetailRepository =
    DetailRepository(countryWebServices)