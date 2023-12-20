package com.presentation.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.model.IndicatorItem
import com.core.common.model.Indicators
import com.core.theme.CryptoColors
import com.core.theme.CryptoTheme
import com.core.theme.LocalTypography
import kotlin.math.abs
// todo
@Composable
internal fun CryptoDetailGraph(
    charsData: List<Float>
) {
    PerformanceChart(
        modifier = Modifier.height(100.dp),
        list = charsData
    )
}


private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)


// Функция для создания градиента от цвета к прозрачности
fun LinearGradientShader(
    x0: Float,
    y0: Float,
    x1: Float,
    y1: Float,
    color: Color,
    endColor: Color
): Brush {
    return LinearGradientShader(
        x0 = x0,
        y0 = y0,
        x1 = x1,
        y1 = y1,
        color = color,
        endColor = endColor
    )
}
@Composable
fun PerformanceChart(modifier: Modifier = Modifier, list: List<Float> = listOf(10f, 20f, 3f)) {
    val zipList: List<Pair<Float, Float>> = list.zipWithNext()

    Row(modifier = modifier) {
        val max = list.max()
        val min = list.min()

        val lineColor =
            if (list.last() > list.first()) Color.Red else Color.Green // <-- Line color is Green if its going up and Red otherwise

        for (pair in zipList) {

            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDraw = {
                    val fromPoint = Offset(
                        x = 0f,
                        y = size.height.times(1 - fromValuePercentage)
                    )
                    val toPoint = Offset(
                        x = size.width,
                        y = size.height.times(1 - toValuePercentage)
                    )

                    // Создаем градиент по линиям
                    val gradientShader = LinearGradientShader(
                        fromPoint.x, fromPoint.y, toPoint.x, toPoint.y,
                        color = lineColor,
                        endColor = Color.Blue
                    )

                    this.drawIntoCanvas {
                        drawLine(
                            brush = gradientShader,
                            start = fromPoint,
                            end = toPoint,
                            strokeWidth = 3f,
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BarPreview() {
    CryptoTheme {
        PerformanceChart()
    }
}