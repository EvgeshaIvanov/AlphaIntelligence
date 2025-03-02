package com.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class CryptoColors(
    val background: Background,
    val text: Text,
    val success: Success,
    val error: Error,
    val warning: Warning
)

@Immutable
data class Background(
    val primary: Color,
    val secondary: Color,
    val white: Color
)

@Immutable
data class Text(
    val primary: Color,
    val secondary: Color
)

@Immutable
data class Success(
    val primary: Color,
    val secondary: Color,
    val dark: Color
)

@Immutable
data class Error(
    val primary: Color,
    val secondary: Color,
    val dark: Color
)

@Immutable
data class Warning(
    val primary: Color,
    val secondary: Color,
    val dark: Color
)

val lightColors = CryptoColors(
    background = Background(
        primary = Color(0xFF241868),
        secondary = Color(0xFFB7A6FC),
        white = Color(0xFFEFEFFE)
    ),
    text = Text(
        primary = Color(0xFFF8F6FF),
        secondary = Color(0xFFAAA7B4),
    ),
    success = Success(
        primary = Color(0xFF20B661),
        secondary = Color(0xFF8BCD8B),
        dark = Color(0xFF13713B)
    ),
    error = Error(
        primary = Color(0xFFE01224),
        secondary = Color(0xFFF61C1C),
        dark = Color(0xFF870A1A)
    ),
    warning = Warning(
        primary = Color(0xFFDB522E),
        secondary = Color(0xFFFAAA78),
        dark = Color(0xFF8C471C)
    )
)

val darkColors = CryptoColors(
    background = Background(
        primary = Color(0xFF241868),
        secondary = Color(0xFF361F89),
        white = Color(0xFFEFEFFE)
    ),
    text = Text(
        primary = Color(0xFF070900),
        secondary = Color(0xFF55584B),
    ),
    success = Success(
        primary = Color(0xFF20B661),
        secondary = Color(0xFF8BCD8B),
        dark = Color(0xFF13713B)
    ),
    error = Error(
        primary = Color(0xFFE01224),
        secondary = Color(0xFFF61C1C),
        dark = Color(0xFF870A1A)
    ),
    warning = Warning(
        primary = Color(0xFFDB522E),
        secondary = Color(0xFFFAAA78),
        dark = Color(0xFF8C471C)
    )
)

val LocalColors = staticCompositionLocalOf<CryptoColors> {
    error("No colors provided")
}