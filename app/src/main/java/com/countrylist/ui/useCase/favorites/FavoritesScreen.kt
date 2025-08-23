package com.countrylist.ui.useCase.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.countrylist.R
import com.countrylist.data.country.model.CountryModel
import com.countrylist.ui.component.CountryItem
import com.countrylist.ui.component.LoadingView
import com.countrylist.ui.theme.CountryListTheme
import com.countrylist.ui.useCase.favorites.uIState.FavoritesUIState
import com.countrylist.ui.useCase.favorites.viewModel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(navigateToDetail: (countryName: String) -> Unit, innerPadding: PaddingValues) {
    val viewModel = koinViewModel<FavoritesViewModel>()
    val favoritesUIState by viewModel.getFavoritesUIState().collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getFavoritesCountries()
    }

    when (val state = favoritesUIState) {
        is FavoritesUIState.Loading -> {
            LoadingView(stringResource(R.string.loading_countries))
        }
        is FavoritesUIState.Success -> {
            DataView(
                innerPadding = innerPadding,
                state,
                navigateToDetail
            )
        }
    }
}
@Composable
private fun DataView(
    innerPadding: PaddingValues,
    uIState: FavoritesUIState.Success,
    navigateToDetail: (countryName: String) -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)){
        LazyColumn {
            items(uIState.countries) { country ->
                CountryItem(
                    country.flags.png,
                    country.name.common,
                    country.name.official,
                    country.getCapitals(),
                    true
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
        DataView(innerPadding = PaddingValues(), uIState = FavoritesUIState.Success(countries = countries), { _ -> },)
    }
}