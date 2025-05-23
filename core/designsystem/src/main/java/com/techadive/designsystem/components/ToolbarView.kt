package com.techadive.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.techadive.designsystem.theme.Movies2cTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarView(
    title: String? = null,
    startIcon: Int? = null,
    startIconDescription: String? = null,
    endContent: (@Composable () -> Unit)? = null,
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
            if (startIcon != null) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp),
                    painter = painterResource(startIcon),
                    contentDescription = startIconDescription.orEmpty(),
                )
            } else {
                startIconDescription?.let {
                    IconButton(onClick = startIconAction) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = startIconDescription,
                            tint = Movies2cTheme.colors.onBackground
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color
        ),
        actions = {
            if (endContent != null) {
                endContent()
            }
        }
    )
}