package com.core.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.core.R

internal val h1 = TextStyle(
    fontSize = 20.sp,
    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
    fontWeight = FontWeight(900),
    color = CryptoColors.TextPrimary,
)

internal val textPrimary = TextStyle(
    fontSize = 16.sp,
    lineHeight = 16.sp,
    fontFamily = FontFamily(Font(R.font.open_sans_medium)),
    fontWeight = FontWeight(404),
    color = CryptoColors.TextPrimary,
)