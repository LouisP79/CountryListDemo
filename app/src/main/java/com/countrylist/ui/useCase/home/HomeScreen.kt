package com.countrylist.ui.useCase.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource


import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.countrylist.R
import com.countrylist.data.country.model.CountryModel
import com.countrylist.ui.component.CountryItem
import com.countrylist.ui.component.LoadingView
import com.countrylist.ui.component.SearchBar
import com.countrylist.ui.component.ShowError
import com.countrylist.ui.theme.CountryListTheme
import com.countrylist.ui.useCase.home.uIState.HomeUIState

import com.countrylist.ui.useCase.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navigateToDetail: (countryName: String) -> Unit, innerPadding: PaddingValues) {
    val viewModel = koinViewModel<HomeViewModel>()
    val homeUIState by viewModel.getHomeUIState().collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (homeUIState is HomeUIState.Loading) {
            viewModel.getCountries()
        }
    }

    when (val state = homeUIState) {
        is HomeUIState.Loading -> {
            LoadingView(stringResource(R.string.loading_countries))
        }

        is HomeUIState.Error -> {
            if(state.message.isNotEmpty()){
                ShowError(state.message) {
                    viewModel.resetView()
                }
            }
            state.errorCode?.let {
                ShowError(stringResource(state.errorCode)) {
                    viewModel.resetView()
                }
            }
        }

        is HomeUIState.Success -> {
            DataView(innerPadding = innerPadding,
                state,
                navigateToDetail
            ) { search ->
                if (search.length >= 2) {
                    viewModel.searchCountries(search)
                } else if (search.isEmpty()) {
                    viewModel.resetView()
                }
            }
        }
    }
}

@Composable
private fun DataView(
    innerPadding: PaddingValues,
    uIState: HomeUIState.Success,
    navigateToDetail: (countryName: String) -> Unit,
    onActionSearch: (String) -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)){

        SearchBar(uIState.searchingValue){ searchValue ->
            onActionSearch(searchValue)
        }

        LazyColumn {
            item{
                if(uIState.countries.isEmpty() && !uIState.isLoadingSearching){
                    Text(stringResource(R.string.empty_list))
                }
            }
            items(uIState.countries) { country ->
                CountryItem(
                    country.flags.png,
                    country.name.common,
                    country.name.official,
                    country.getCapitals()
                ){
                    navigateToDetail(it)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DataViewPreview() {
    CountryListTheme {
        val countries = listOf(CountryModel(),
            CountryModel(),
            CountryModel()
        )
        DataView(innerPadding = PaddingValues(), uIState = HomeUIState.Success(countries = countries), { _ -> }, { search -> })
    }
}