package com.techadive.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.designsystem.R

@Composable
fun InternetErrorView(
    paddingValues: PaddingValues,
    message: String, isActionAble: Boolean = true,
    refresh: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Movies2cTheme.colors.background)
            .padding(paddingValues)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val composition by
            rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_error))

            LottieAnimation(
                modifier = Modifier
                    .size(150.dp),
                composition = composition,
                iterations = 1,
            )

            Text(
                text = message,
                style = Movies2cTheme.typography.h3,
                color = Movies2cTheme.colors.onBackground,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isActionAble) {
                IconButton(
                    onClick = {
                        refresh()
                    },
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(R.string.refresh),
                            tint = Movies2cTheme.colors.primary
                        )

                        Text(
                            text = stringResource(R.string.refresh),
                            style = Movies2cTheme.typography.label,
                            color = Movies2cTheme.colors.primary,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}