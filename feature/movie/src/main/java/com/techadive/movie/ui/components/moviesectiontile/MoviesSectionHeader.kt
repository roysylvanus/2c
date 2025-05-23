package com.techadive.movie.ui.components.moviesectiontile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.techadive.common.R
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.designsystem.theme.Palette

@Composable
fun MoviesSectionHeader(
    modifier: Modifier = Modifier,
    titleResource: Int,
    seeAllClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = stringResource(titleResource),
            style = Movies2cTheme.typography.h3,
            color = Movies2cTheme.colors.onBackground
        )

        TextButton(modifier = Modifier.alignByBaseline(), onClick = { seeAllClick() }) {
            Row {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = stringResource(R.string.see),
                    style = Movies2cTheme.typography.label,
                    color = Movies2cTheme.colors.onBackground
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = stringResource(R.string.all),
                    modifier = Modifier
                        .alignByBaseline()
                        .drawBehind {
                            val underlineHeight = 2.dp.toPx()
                            val y = size.height // Bottom of the text
                            drawLine(
                                color = Palette.orange, // Underline color only
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = underlineHeight
                            )
                        },
                    style = Movies2cTheme.typography.label,
                    color = Movies2cTheme.colors.onBackground
                )
            }
        }
    }

}