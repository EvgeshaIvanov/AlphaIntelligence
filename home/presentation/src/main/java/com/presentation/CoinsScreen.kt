package com.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
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
                        LazyColumn(Modifier.padding(top = 8.dp)) {
                            items(20) {
                                Shimmer()
                            }
                        }
                    }

                    CoinsViewState.Progress.Content -> {
                        LazyColumn(Modifier.padding(top = 8.dp)) {
                            items(items = state.coins) { coin ->
                                CoinsItem(name = coin.name, price = coin.price.toString())
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
fun CoinsItem(name: String, price: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Black)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = name, color = Color.White)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = price, color = Color.White)
    }
}

@Preview
@Composable
private fun ShimmerPreview() {
    Shimmer()
}

@Preview(showBackground = true)
@Composable
private fun CoinsItemPreview() {
    CoinsItem(name = "Bitcoin", price = "2424.2424")
}