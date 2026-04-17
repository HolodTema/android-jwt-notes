package com.terabyte.jwtnotes.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.visible(isVisible: Boolean): Modifier = this.then(
    if (!isVisible) {
        Modifier.graphicsLayer { alpha = 0f }
    } else {
        Modifier
    }
)