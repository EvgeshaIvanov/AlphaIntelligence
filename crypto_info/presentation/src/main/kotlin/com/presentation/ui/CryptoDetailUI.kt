package com.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.core.common.model.IndicatorItem
import com.core.theme.CryptoColors
import com.core.theme.CryptoTheme
import com.core.theme.LocalTypography

@Composable
internal fun CryptoDetailHeader(
    imageUrl: String,
    title: String,
    price: String,
    indicator: IndicatorItem?,
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CryptoDetailToolbar(onBackClick = onBackClick)
            AsyncImage(
                modifier = Modifier
                    .size(64.dp)
                    .padding(top = 16.dp),
                model = imageUrl,
                placeholder = painterResource(id = com.core.R.drawable.baseline_mood_bad_24),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = title,
                style = LocalTypography.current.h1
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = price,
                style = LocalTypography.current.h1
            )
            indicator?.let {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = it.value,
                    style = LocalTypography.current.h1
                )
            }
        }
    }
}

@Composable
private fun CryptoDetailToolbar(
    onBackClick: () -> Unit,
) {

    val shape = RoundedCornerShape(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
        bottomEnd = CornerSize(16.dp),
        bottomStart = CornerSize(16.dp)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier

        ) {
            IconButton(
                modifier = Modifier.padding(16.dp),
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = CryptoColors.TextPrimary,
                    contentDescription = null
                )
            }
        }
        Divider(color = CryptoColors.CardColor, thickness = 4.dp)
    }
}

@Composable
private fun CryptoDetailGraph() {
    // TODO
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CryptoDetailHeaderrPreview() {
    CryptoTheme {
        CryptoDetailHeader(
            imageUrl = "",
            title = "Bitcoin",
            price = "$60,000",
            indicator = null,
            onBackClick = {}
        )
    }
}