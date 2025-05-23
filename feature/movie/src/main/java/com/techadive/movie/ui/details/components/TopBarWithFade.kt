package com.techadive.movie.ui.details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.techadive.common.utils.AppRoutes
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithFade(
    scrollState: ScrollState,
    endIcon: ImageVector?,
    back: () -> Unit,
    shareUrl: () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (scrollState.value > 100) 1f else 0f,
        label = "Toolbar Alpha"
    )

    ToolbarView(
        title = stringResource(com.techadive.common.R.string.movie_details),
        startIconAction = back,
        startIconDescription = stringResource(com.techadive.common.R.string.back),
        color = Movies2cTheme.colors.background.copy(alpha = alpha),
        endContent = {
            endIcon?.let {
                IconButton(onClick = {
                    shareUrl()
                }) {
                    Icon(
                        imageVector = it,
                        contentDescription = stringResource(com.techadive.common.R.string.search),
                        tint = Movies2cTheme.colors.onBackground
                    )
                }
            }
        },
    )
}