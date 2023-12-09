package com.core.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CoinsLoader(
    circleColor: Color = Color(0xFFC7C8CF),
    animationDelay: Int = 1000
) {
    var circleScale by remember { mutableStateOf(0f) }

    val circleScaleAnimate = animateFloatAsState(
        label = "circleScale",
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        )
    )

    LaunchedEffect(Unit) {
        circleScale = 1f
    }

    Box(
        modifier = Modifier
            .size(size = 64.dp)
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = 4.dp,
                color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = circleScaleAnimate.value,
            color = circleColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun LoadingAnimationPreview() {
    CoinsLoader()
}