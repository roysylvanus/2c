package com.techadive.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.techadive.designsystem.theme.Movies2cTheme

@Composable
fun ReadonlyTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    label: Int? = null,
    icon: Int,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .border(
                BorderStroke(1.dp, Movies2cTheme.colors.onBackground),
                shape = Movies2cTheme.shapes.large
            )
    ) {
        TextField(
            value = value.orEmpty(),
            onValueChange = onValueChange,
            label = {
                label?.let {
                    Text(
                        text = stringResource(label),
                        color = Movies2cTheme.colors.onBackground,
                        style = Movies2cTheme.typography.body4
                    )
                }
            },
            shape = Movies2cTheme.shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Movies2cTheme.colors.background,
                unfocusedTextColor = Movies2cTheme.colors.background,
                cursorColor = Movies2cTheme.colors.background,
                errorLabelColor = Movies2cTheme.colors.error,
                focusedLabelColor = Movies2cTheme.colors.background,
                errorIndicatorColor = Movies2cTheme.colors.error,
                focusedIndicatorColor = Movies2cTheme.colors.background,
                unfocusedIndicatorColor = Movies2cTheme.colors.background,
                focusedContainerColor = Movies2cTheme.colors.background,
                unfocusedContainerColor = Movies2cTheme.colors.background,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    tint = Movies2cTheme.colors.primary,
                )
            }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}