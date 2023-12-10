package com.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class CryptoTypography(
    val h1: TextStyle,
    val textPrimary: TextStyle,
)

val LocalTypography = staticCompositionLocalOf<CryptoTypography> {
    error("No font provided")
}