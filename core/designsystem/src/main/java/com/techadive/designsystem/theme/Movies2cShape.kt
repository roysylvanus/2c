package com.techadive.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

val shapes = Movies2cShape(
    small = RoundedCornerShape(4),
    medium = RoundedCornerShape(8),
    large = RoundedCornerShape(16),
    extraLarge = RoundedCornerShape(20)
)

@Immutable
data class Movies2cShape(
    val small: Shape,
    val medium: Shape,
    val large: Shape,
    val extraLarge: Shape,
)

internal val LocalMovies2cShapes =
    staticCompositionLocalOf<Movies2cShape> { error("No Movies2cShapes provided") }