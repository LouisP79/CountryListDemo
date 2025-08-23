package com.countrylist.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.countrylist.R
import com.countrylist.ui.theme.Text
import com.countrylist.ui.theme.TextSecondary

@Composable
fun CountryItem(imgSrc: String, common: String,
                        official: String, capital: String,
                        isFavorite: Boolean = false,
                        onClick: (common: String) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick(common) }) {
        AsyncImage(
            model = imgSrc,
            contentDescription = "",
            modifier = Modifier
                .size(size = 135.dp)
                .padding(horizontal = 20.dp,
                    vertical = 5.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(common, color = Color.Black)
                if(isFavorite) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_favorite_on),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                }
            }
            Text(official, color = Text)
            Text(capital, color = TextSecondary)
        }
    }
}