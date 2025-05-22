package com.techadive.movie.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techadive.designsystem.theme.Movies2cTheme

@Composable
fun IconText(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier,
    iconTint: Color = Movies2cTheme.colors.primary,
    textColor: Color = Movies2cTheme.colors.onBackground,
    iconSize: Dp = 20.dp,
    spacing: Dp = 6.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            color = textColor,
            style = Movies2cTheme.typography.body4
        )
    }
}