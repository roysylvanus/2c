package com.techadive.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.techadive.designsystem.theme.Movies2cTheme
import com.techadive.designsystem.R

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    message: String? = null,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(1000)),
    ) {
        Box(
            modifier = modifier
                .background(Movies2cTheme.colors.background)
                .padding(paddingValues)
        ) {
            val composition by
            rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_lottie))

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                LottieAnimation(
                    modifier = Modifier
                        .size(150.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )

                if (message != null) {
                    Text(
                        text = message,
                        style = Movies2cTheme.typography.body4,
                        color = Movies2cTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}