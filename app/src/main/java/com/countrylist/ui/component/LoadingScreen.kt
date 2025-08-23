package com.countrylist.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.countrylist.ui.theme.CountryListTheme

@Composable
@SuppressLint("ModifierParameter")
fun LoadingView(loadingMessage: String, modifier: Modifier? = null){
    Box(modifier = modifier ?: Modifier.fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding(),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(loadingMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingViewPreview() {
    CountryListTheme {
        LoadingView("Loading Message")
    }
}