package com.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.core.common.model.IndicatorItem
import com.core.compose.CoinsLoader
import com.core.compose.bounceClick
import com.core.compose.rememberClick
import com.core.theme.CryptoColors
import com.core.theme.CryptoTheme
import com.core.theme.LocalTypography
import com.presentation.contract.CoinsAction
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState
import kotlinx.coroutines.flow.collectLatest

class CoinsScreen : Screen {
    // TODO(remove this annotation later)
    @SuppressLint("NewApi")
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CoinsViewModel>()
        val handler = rememberClick(viewModel::handleEvent)

        val state by viewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(viewModel.action) {
            viewModel.action.collectLatest { action ->
                when (action) {
                    is CoinsAction.OpenDetailScreen -> navigator.push(CryptoDetailScreen(action.coinEntity))
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CryptoColors.BackgroundPrimary
        ) {
            Crossfade(targetState = state.progress, label = "News") {
                when (it) {
                    CoinsViewState.Progress.Loading -> {
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CoinsLoader()
                        }
                    }

                    CoinsViewState.Progress.Content -> {
                        LazyColumn(Modifier.padding(top = 8.dp)) {
                            items(
                                key = { coin ->
                                    "item_key_${coin.name}_${coin.id}"
                                },
                                items = state.coins
                            ) { coin ->
                                CoinsItem(
                                    name = coin.name,
                                    price = coin.price,
                                    imageUrl = coin.icon,
                                    indicator = coin.indicators?.priceChange1d,
                                    onClick = {
                                        handler.invoke(CoinsEvent.OnItemClick(coin))
//                                        viewModel.setEvent(CoinsEvent.OnItemClick(coin))
                                    }
                                )
                            }
                        }
                    }

                    CoinsViewState.Progress.Error -> {
                        // TODO
                    }
                }
            }
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
            .background(CryptoColors.CardColor)
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
                style = LocalTypography.current.textPrimary
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = LocalTypography.current.h1
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
        IndicatorItem.State.Increase -> CryptoColors.Increase
        IndicatorItem.State.Decrease -> CryptoColors.Decrease
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
            style = LocalTypography.current.textPrimary
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