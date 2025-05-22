package com.techadive.movie.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.techadive.common.models.Genre
import com.techadive.designsystem.theme.Movies2cTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun GenreChips(genres: List<Genre>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        genres.forEach {
            Chip(
                onClick = {},
                colors = ChipDefaults.chipColors(
                    backgroundColor = Movies2cTheme.colors.onBackground.copy(
                        alpha = 0.25f
                    )
                )
            ) {
                Text(
                    it.name,
                    style = Movies2cTheme.typography.h7,
                    color = Movies2cTheme.colors.onBackground
                )
            }
        }
    }
}