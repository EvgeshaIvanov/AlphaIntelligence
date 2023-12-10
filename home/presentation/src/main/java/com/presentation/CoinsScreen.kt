package com.presentation

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
import androidx.compose.ui.graphics.Color
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
import com.presentation.contract.CoinsAction
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState

class CoinsScreen : Screen {
    @Composable
    override fun Content() {
        val coinsViewModel = rememberScreenModel<CoinsViewModel>()

        val state by coinsViewModel.uiState.collectAsState()
        val action by coinsViewModel.action.collectAsState(initial = null)

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(action) {
            when (val someNew = action) {
                CoinsAction.Close -> TODO()
                is CoinsAction.OpenDetailScreen -> {

                    navigator.push(CryptoDetailScreen(someNew.coinEntity))
                }
                null -> Unit
            }
        }

        LaunchedEffect(Unit) {
            coinsViewModel.setEvent(CoinsEvent.OnCreate)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF000000)
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
                            items(items = state.coins) { coin ->
                                CoinsItem(
                                    name = coin.name,
                                    price = coin.price,
                                    imageUrl = coin.icon,
                                    indicator = coin.indicator,
                                    onClick = {
                                        coinsViewModel.setEvent(CoinsEvent.OnItemClick(coin))
                                    }
                                )
                            }
                        }
                    }

                    CoinsViewState.Progress.Error -> Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxSize()
                    )
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
            .background(Color(0xFF14213d))
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
                color = Color.White,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                color = Color.White
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
        IndicatorItem.State.Increase -> Color(0xFF3dfc03)
        IndicatorItem.State.Decrease -> Color(0xFFc91c33)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painter, contentDescription = null, tint = color)
        Text(text = indicator.value, color = color)
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinsItemPreview() {
    CoinsItem(
        name = "Bitcoin",
        price = "$2,424",
        imageUrl = "",
        indicator = IndicatorItem(
            value = "2.45",
            state = IndicatorItem.State.Increase
        ),
        onClick = {}
    )
}