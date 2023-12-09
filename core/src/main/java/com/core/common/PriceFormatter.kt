package com.core.common

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    formatter.maximumFractionDigits = if (price >= 1) 0 else 2

    return formatter.format(price)
}