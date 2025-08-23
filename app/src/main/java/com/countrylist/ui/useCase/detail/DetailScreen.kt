package com.countrylist.ui.useCase.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.countrylist.R
import com.countrylist.data.country.model.CountryDetailModel
import com.countrylist.data.country.model.CountryModel
import com.countrylist.ui.component.LoadingView
import com.countrylist.ui.component.ShowError
import com.countrylist.ui.theme.CountryListTheme
import com.countrylist.ui.theme.TextSecondary
import com.countrylist.ui.useCase.detail.uIState.DetailUIState
import com.countrylist.ui.useCase.detail.viewModel.DetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(countryName: String, onBack: () -> Unit) {
    val viewModel = koinViewModel<DetailViewModel>()
    val detailUIState by viewModel.getDetailUIState().collectAsStateWithLifecycle()
    var favoriteIcon by remember { mutableIntStateOf(R.drawable.ic_favorite_off) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (detailUIState is DetailUIState.Loading) {
            viewModel.getCountries(countryName)
        }
    }

    when (val state = detailUIState) {
        is DetailUIState.Error -> {
            if(state.message.isNotEmpty()){
                ShowError(state.message) {
                    onBack()
                }
            }
            state.errorCode?.let {
                ShowError(stringResource(state.errorCode)) {
                    onBack()
                }
            }
        }

        DetailUIState.Loading -> LoadingView(stringResource(R.string.loading_country_detail))
        is DetailUIState.Success -> {
            val countryAux = CountryModel(state.detailCountry.name,
                state.detailCountry.capital,
                state.detailCountry.flags)
            favoriteIcon = if(viewModel.isCountryFavorite(countryAux.name.common)) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            DataView(state.detailCountry, favoriteIcon) {

                viewModel.saveDeleteCountry(countryAux, context)
                favoriteIcon = if(favoriteIcon == R.drawable.ic_favorite_off) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            }
        }
    }
}

@Composable
private fun DataView(countryDetail: CountryDetailModel, favIcon: Int, favClick: () -> Unit){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 25.dp, vertical = 10.dp)
    ) {
        Row {
            AsyncImage(
                model = countryDetail.flags.png,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .width(200.dp)
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(favIcon),
                contentDescription = null,
                modifier = Modifier.padding(top = 10.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable{
                        favClick()
                    })
        }

        Text(countryDetail.name.common,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp)
        Text(countryDetail.name.official,
            color = TextSecondary,
            fontSize = 25.sp)
        Row(modifier = Modifier.padding(vertical = 15.dp)) {
            Column(modifier = Modifier.weight(1f).padding(end = 10.dp)) {
                BasicImg(stringResource(R.string.coat_of_arms), countryDetail.coatOfArms.png)
                BasicInfo(stringResource(R.string.region),countryDetail.region)
                BasicInfo(stringResource(R.string.subregion),countryDetail.subregion)
                BasicInfo(stringResource(R.string.subregion),countryDetail.getCapitals())
                BasicInfo(stringResource(R.string.area),stringResource(R.string.blank_long, countryDetail.area))
            }
            Column(modifier = Modifier.weight(1f)) {
                BasicInfo(stringResource(R.string.population),stringResource(R.string.blank_long, countryDetail.population))
                BasicInfo(stringResource(R.string.languages),countryDetail.getLanguages())
                CarDriverSiteComp(countryDetail.car.side)
                BasicInfo(stringResource(R.string.currency),countryDetail.getCurrencies())
                BasicInfo(stringResource(R.string.timezones),countryDetail.getTimeZones())
            }
        }
    }
}

@Composable
private fun BasicImg(label: String, img: String){
    Text(label,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
    AsyncImage(
        model = img,
        contentDescription = null,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width(50.dp)
            .height(50.dp)
    )
}

@Composable
private fun CarDriverSiteComp(value: String){
    Text(
        stringResource(R.string.car_driver_site),
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
    Row(modifier = Modifier
        .padding(bottom = 10.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_car),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
                .width(25.dp)
                .height(25.dp)
        )
        Text(if(value == "right") stringResource(R.string.right) else stringResource(R.string.left),
            color = TextSecondary,
            fontSize = 22.sp)
    }

}

@Composable
private fun BasicInfo(label: String, value: String){
    Text(label,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
    Text(value,
        Modifier.padding(bottom = 10.dp),
        color = TextSecondary,
        fontSize = 22.sp)
}



@Preview(showBackground = true)
@Composable
fun DataViewPreview() {
    CountryListTheme {
        DataView(CountryDetailModel(), R.drawable.ic_favorite_off) {}
    }
}