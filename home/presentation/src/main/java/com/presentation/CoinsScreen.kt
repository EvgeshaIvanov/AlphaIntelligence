package com.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.core.compose.CoinsLoader
import com.core.compose.bounceClick
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState

class CoinsScreen : Screen {
    @Composable
    override fun Content() {
        val coinsViewModel = rememberScreenModel<CoinsViewModel>()

        val state by coinsViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            coinsViewModel.setEvent(CoinsEvent.OnCreate)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF190482)
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
                                    indicator = coin.indicator
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
fun Shimmer() {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        ), label = "shimmer"
    )
    val brush = linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )

    Spacer(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(brush = brush)
            .padding(16.dp)
            .height(18.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun CoinsItem(
    name: String,
    price: String,
    imageUrl: String,
    indicator: CoinsViewState.Indicator?,
) {
    Column(
        modifier = Modifier
            .bounceClick(onClick = { })
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF293368))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(64.dp),
                model = imageUrl,
                placeholder = painterResource(id = R.drawable.baseline_mood_bad_24),
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
private fun CoinIndicatorItem(indicator: CoinsViewState.Indicator) {
    val painter = painterResource(
        id = when (indicator.state) {
            CoinsViewState.Indicator.State.Increase -> R.drawable.graph_up
            CoinsViewState.Indicator.State.Decrease -> R.drawable.graph_down
        }
    )

    val color = when (indicator.state) {
        CoinsViewState.Indicator.State.Increase -> Color(0xFF3dfc03)
        CoinsViewState.Indicator.State.Decrease -> Color(0xFFc91c33)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painter, contentDescription = null, tint = color)
        Text(text = indicator.value, color = color)
    }
}

@Preview
@Composable
private fun ShimmerPreview() {
    Shimmer()
}

@Preview(showBackground = true)
@Composable
private fun CoinItemIndicatorPreview() {
    CoinIndicatorItem(
        indicator = CoinsViewState.Indicator(
            "2.45",
            CoinsViewState.Indicator.State.Increase
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun CoinsItemPreview() {
    CoinsItem(
        name = "Bitcoin",
        price = "$2,424",
        imageUrl = "",
        indicator = CoinsViewState.Indicator(
            value = "2.45",
            state = CoinsViewState.Indicator.State.Increase
        )
    )
}