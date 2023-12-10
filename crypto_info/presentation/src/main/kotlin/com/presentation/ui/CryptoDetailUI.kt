package com.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.core.common.model.IndicatorItem

@Composable
internal fun CryptoDetailToolbar(
    imageUrl: String,
    title: String,
    price: String,
    indicator: IndicatorItem?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.size(64.dp),
                model = imageUrl,
                placeholder = painterResource(id = com.core.R.drawable.baseline_mood_bad_24),
                contentDescription = null
            )
            Text(text = title)
            Text(text = price)
            indicator?.let {
                Text(text = it.value)
            }
        }
    }
}

@Composable
private fun CryptoDetailGraph() {
    // TODO
}

@Preview(showBackground = true)
@Composable
private fun CryptoDetailToolbarPreview() {
    CryptoDetailToolbar(
        imageUrl = "",
        title = "Bitcoin",
        price = "$60,000",
        indicator = null,
    )
}