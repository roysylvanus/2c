package com.techadive.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.techadive.data.stores.settings.AppTheme
import com.techadive.designsystem.theme.Palette.blue
import com.techadive.designsystem.theme.Palette.darkBlue
import com.techadive.designsystem.theme.Palette.darkGrey
import com.techadive.designsystem.theme.Palette.darkGreyA9
import com.techadive.designsystem.theme.Palette.darkOrange
import com.techadive.designsystem.theme.Palette.grey
import com.techadive.designsystem.theme.Palette.lightDarkGrey
import com.techadive.designsystem.theme.Palette.lightGrey
import com.techadive.designsystem.theme.Palette.orange
import com.techadive.designsystem.theme.Palette.red
import com.techadive.designsystem.theme.Palette.white

val LightThemeColors by lazy {
    Movies2cColor(
        primary = orange,
        primaryVariant = darkOrange,
        secondary = blue,
        secondaryVariant = darkBlue,
        background = white,
        backgroundVariant = lightGrey,
        surface = white,
        error = red,
        onPrimary = white,
        onSecondary = darkGrey,
        onBackground = darkGrey,
        onSurface = darkGreyA9,
        onError = white,
        description = darkBlue,
    )
}

val DarkThemeColors by lazy {
    Movies2cColor(
        primary = darkOrange,
        primaryVariant = orange,
        secondary = blue,
        secondaryVariant = darkBlue,
        background = darkGrey,
        backgroundVariant = lightDarkGrey,
        surface = darkGrey,
        error = red,
        onPrimary = white,
        onSecondary = white,
        onBackground = white,
        onSurface = grey,
        onError = white,
        description = blue,
    )
}

object Movies2cTheme {
    val colors: Movies2cColor
        @Composable
        get() = LocalMovies2cColors.current

    val typography: Movies2cTypography
        @Composable
        get() = LocalMovies2cTypography.current

    val shapes: Movies2cShape
        @Composable
        get() = LocalMovies2cShapes.current
}

@Composable
fun Movies2cTheme(appTheme: AppTheme = AppTheme.MODE_AUTO, content: @Composable () -> Unit) {

    val darkTheme = when (appTheme) {
        AppTheme.MODE_AUTO -> isSystemInDarkTheme()
        AppTheme.MODE_DARK -> true
        AppTheme.MODE_LIGHT -> false
    }

    val colors = if (darkTheme) DarkThemeColors else LightThemeColors

    CompositionLocalProvider(
        LocalMovies2cColors provides colors,
        LocalMovies2cTypography provides typography,
        LocalMovies2cShapes provides shapes,
        content = content
    )
}