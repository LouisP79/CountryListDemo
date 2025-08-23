package com.countrylist.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.countrylist.R
import com.countrylist.ui.theme.CountryListTheme
import com.countrylist.ui.theme.Text

@Composable
fun SearchBar(textValue: String, onSearch: (String) -> Unit){
    var searchText by remember { mutableStateOf("") }
    searchText = textValue
    Surface(shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 1.dp,
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if(searchText.isNotEmpty()){
                SearchBarIcon(icon = R.drawable.ic_close,
                    clickListener = {
                        searchText = ""
                        onSearch(searchText)
                    })
            }
            TextField(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                    onSearch(searchText)
                                },
                singleLine = true,
                modifier = Modifier.weight(weight = 1f),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor  = Color.Transparent,
                    unfocusedContainerColor  = Color.Transparent),
                placeholder = {
                    Text(text = stringResource(R.string.search_hint),
                        color = Text)
                }
            )
        }
    }
}

@Composable
private fun SearchBarIcon(icon: Int, clickListener: () -> Unit){
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        colorFilter = ColorFilter.tint(color = Text),
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable { clickListener.invoke() }
            .size(size = 35.dp)
            .padding(all = 5.dp)
    )
}

@Preview()
@Composable
fun SearchBarNoMenuPreview() {
    CountryListTheme {
        SearchBar("") {}
    }
}