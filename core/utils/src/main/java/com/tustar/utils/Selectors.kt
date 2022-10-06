package com.tustar.utils

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun selector(normal: Color, pressed: Color): Pair<MutableInteractionSource, Color> {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val color = if (isFocused || isPressed || isHovered) {
        pressed
    } else {
        normal
    }
    return Pair(interactionSource, color)
}

@Composable
fun tint() = selector(Color.White, Color(0x66FFFFFF))