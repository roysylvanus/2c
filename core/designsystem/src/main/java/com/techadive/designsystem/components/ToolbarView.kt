package com.techadive.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.techadive.designsystem.theme.Movies2cTheme

@Composable
fun ToolbarView(
    title: String? = null,
    startIcon: IconWithDescription? = null,
    startIconAction: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp)
            .fillMaxWidth()
            .background(Movies2cTheme.colors.background)
    ) {

        startIcon?.let {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                onClick = {
                    startIconAction()
                }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = startIcon.icon,
                    contentDescription = stringResource(startIcon.description),
                    tint = Movies2cTheme.colors.onBackground
                )
            }
        }

        title?.let {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = it,
                textAlign = TextAlign.Center,
                style = Movies2cTheme.typography.h2,
                color = Movies2cTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class IconWithDescription(
    val icon: ImageVector,
    val description: Int,
)