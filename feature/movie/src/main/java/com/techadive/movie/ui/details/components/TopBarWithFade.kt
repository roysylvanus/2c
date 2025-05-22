package com.techadive.movie.ui.details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.techadive.designsystem.components.ToolbarView
import com.techadive.designsystem.theme.Movies2cTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithFade(scrollState: ScrollState, back: () -> Unit, shareUrl: () -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = if (scrollState.value > 100) 1f else 0f,
        label = "Toolbar Alpha"
    )

    ToolbarView(
        title = stringResource(com.techadive.common.R.string.movie_details),
        startIconAction = back,
        startIconDescription = stringResource(com.techadive.common.R.string.back),
        color = Movies2cTheme.colors.background.copy(alpha = alpha),
        endIcon = Icons.Filled.Share,
        endIconAction = {
            shareUrl()
        }
    )
}