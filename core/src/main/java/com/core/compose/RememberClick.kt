package com.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember

@Composable
inline fun <T> rememberClick(crossinline block: @DisallowComposableCalls (T) -> Unit): (T) -> Unit =
    remember {
        { item -> block(item) }
    }