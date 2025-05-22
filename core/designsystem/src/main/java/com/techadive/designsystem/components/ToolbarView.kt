package com.techadive.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.techadive.designsystem.theme.Movies2cTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarView(
    title: String? = null,
    startIconDescription: String? = null,
    endIconDescription: String? = null,
    endIcon: ImageVector? = null,
    endIconAction: ()-> Unit,
    startIconAction: () -> Unit = {},
    color: Color = Movies2cTheme.colors.background
) {

    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = Movies2cTheme.typography.h2,
                    color = Movies2cTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            startIconDescription?.let {
                IconButton(onClick = startIconAction) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = startIconDescription,
                        tint = Movies2cTheme.colors.onBackground
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color
        ),
        actions = {
            endIcon?.let {
                IconButton(onClick = endIconAction) {
                    Icon(
                        imageVector = endIcon,
                        contentDescription = endIconDescription,
                        tint = Movies2cTheme.colors.onBackground
                    )
                }
            }
        }
    )
}