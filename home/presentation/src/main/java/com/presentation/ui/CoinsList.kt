package com.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.core.common.model.IndicatorItem
import com.core.compose.bounceClick
import com.core.theme.Crypto
import com.core.theme.CryptoTheme
import com.presentation.contract.CoinsViewState.Coin
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CoinsList(
    coins: ImmutableList<Coin>,
    onItemClick: (Coin) -> Unit
) {
    LazyColumn(Modifier.padding(top = 8.dp)) {
        items(
            key = { coin -> "item_key_${coin.name}_${coin.id}" },
            items = coins
        ) { coin ->
            CoinsItem(
                name = coin.name,
                price = coin.price,
                imageUrl = coin.icon,
                indicator = coin.indicators?.priceChange1d,
                onClick = {
                    onItemClick(coin)
                }
            )
        }
    }
}

@Composable
private fun CoinsItem(
    name: String,
    price: String,
    imageUrl: String,
    indicator: IndicatorItem?,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .bounceClick(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Crypto.color.background.secondary)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(64.dp),
                model = imageUrl,
                placeholder = painterResource(id = com.core.R.drawable.baseline_mood_bad_24),
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = price,
                style = Crypto.typography.textPrimary
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = Crypto.typography.h1
            )
            Spacer(modifier = Modifier.weight(1f))
            indicator?.let {
                CoinIndicatorItem(indicator = it)
            }
        }
    }
}

@Composable
private fun CoinIndicatorItem(indicator: IndicatorItem) {
    val painter = painterResource(
        id = when (indicator.state) {
            IndicatorItem.State.Increase -> com.core.R.drawable.graph_up
            IndicatorItem.State.Decrease -> com.core.R.drawable.graph_down
        }
    )

    val color = when (indicator.state) {
        IndicatorItem.State.Increase -> Crypto.color.success.primary
        IndicatorItem.State.Decrease -> Crypto.color.error.primary
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = color
        )
        Text(
            text = "${indicator.value}%",
            color = color,
            style = Crypto.typography.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinsItemPreview() {
    CryptoTheme {
        CoinsItem(
            name = "Bitcoin",
            price = "$2,424",
            imageUrl = "",
            indicator = IndicatorItem(
                value = 2.45f,
                state = IndicatorItem.State.Increase
            ),
            onClick = {}
        )
    }
}