package com.techadive.movie.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.techadive.common.models.ProductionCompany
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.network.utils.ApiUtils

@Composable
fun ProductionCompaniesView(companies: List<ProductionCompany>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(com.techadive.common.R.string.produced_by),
            style = Movies2cTheme.typography.h5,
            color = Movies2cTheme.colors.onSurface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            companies.forEach { company ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (company.logoPath?.isNotEmpty() == true) {
                        AsyncImage(
                            model = ApiUtils.IMAGE_URL + company.logoPath,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Movies2cTheme.colors.onBackground),
                        )
                    }
                }
            }
        }
    }
}
