package com.countrylist.di

import com.countrylist.ui.useCase.detail.viewModel.DetailViewModel
import com.countrylist.ui.useCase.favorites.viewModel.FavoritesViewModel
import com.countrylist.ui.useCase.home.viewModel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::FavoritesViewModel)
}