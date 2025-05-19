package com.techadive.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.techadive.designsystem.R

@Immutable
data class Movies2cTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val h7: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val body5: TextStyle,
    val label: TextStyle,
    val quotes: TextStyle,
    val button: TextStyle,
    val bodyLight: TextStyle,
)

@Immutable
data class Movie2cTextSize(
    val h1: TextUnit,
    val h2: TextUnit,
    val h3: TextUnit,
    val h4: TextUnit,
    val h5: TextUnit,
    val h6: TextUnit,
    val h7: TextUnit,
    val body1: TextUnit,
    val body2: TextUnit,
    val body3: TextUnit,
    val body4: TextUnit,
    val body5: TextUnit,
    val label: TextUnit,
    val quotes: TextUnit,
    val button: TextUnit,
    val bodyLight: TextUnit,
)

@Immutable
data class Movie2cTextLineHeight(
    val h1: TextUnit,
    val h2: TextUnit,
    val h3: TextUnit,
    val h4: TextUnit,
    val h5: TextUnit,
    val h6: TextUnit,
    val h7: TextUnit,
    val body1: TextUnit,
    val body2: TextUnit,
    val body3: TextUnit,
    val body4: TextUnit,
    val body5: TextUnit,
    val label: TextUnit,
    val quotes: TextUnit,
    val button: TextUnit,
    val bodyLight: TextUnit,
)

object Artifakt {
    val normal = Font(R.font.rethinksans_regular, FontWeight.Normal)
    val medium = Font(R.font.rethinksans_medium, FontWeight.Medium)
    val normalItalic = Font(R.font.rethinksans_italic, FontWeight.Normal)
    val lightItalic = Font(R.font.rethinksans_italic, FontWeight.Light)
    val light = Font(R.font.rethinksans_regular, FontWeight.Light)
    val extraLight = Font(R.font.rethinksans_regular, FontWeight.ExtraLight)
    val bold = Font(R.font.rethinksans_bold, FontWeight.Bold)
    val extraBold = Font(R.font.rethinksans_extrabold, FontWeight.ExtraBold)
    val semiBold = Font(R.font.rethinksans_semibold, FontWeight.SemiBold)
    val thin = Font(R.font.rethinksans_regular, FontWeight.Thin)
    val boldItalic = Font(R.font.rethinksans_bolditalic, FontWeight.Bold)
    val extraBoldItalic = Font(R.font.rethinksans_extrabolditalic, FontWeight.ExtraBold)
}

// Text Sizes
val movie2cTextSize = Movie2cTextSize(
    h1 = 40.sp,
    h2 = 31.sp,
    h3 = 20.sp,
    h4 = 20.sp,
    h5 = 16.sp,
    h6 = 14.sp,
    h7 = 14.sp,
    body1 = 24.sp,
    body2 = 20.sp,
    body3 = 16.sp,
    body4 = 14.sp,
    body5 = 12.sp,
    label = 16.sp,
    quotes = 14.sp,
    button = 22.sp,
    bodyLight = 20.sp,
)

val movie2cTextLineHeight = Movie2cTextLineHeight(
    h1 = movie2cTextSize.h1.times(1.466.sp.value),
    h2 = movie2cTextSize.h2.times(1.555.sp.value),
    h3 = movie2cTextSize.h3.times(1.5.sp.value),
    h4 = movie2cTextSize.h4.times(1.5.sp.value),
    h5 = movie2cTextSize.h4.times(1.5.sp.value),
    h6 = movie2cTextSize.h4.times(1.5.sp.value),
    h7 = movie2cTextSize.h4.times(1.5.sp.value),
    body1 = movie2cTextSize.body1.times(1.429.sp.value),
    body2 = movie2cTextSize.body2.times(1.429.sp.value),
    body3 = movie2cTextSize.body3.times(1.429.sp.value),
    body4 = movie2cTextSize.body3.times(1.429.sp.value),
    body5 = movie2cTextSize.body3.times(1.429.sp.value),
    label = movie2cTextSize.body3.times(1.429.sp.value),
    button = movie2cTextSize.body3.times(1.429.sp.value),
    quotes = movie2cTextSize.body3.times(1.429.sp.value),
    bodyLight = movie2cTextSize.bodyLight.times(1.429.sp.value)
)

val typography = Movies2cTypography(
    h1 = TextStyle(
        fontFamily = FontFamily(Artifakt.extraBold),
        fontSize = movie2cTextSize.h1,
        lineHeight = movie2cTextLineHeight.h1,
    ),
    h2 = TextStyle(
        fontFamily = FontFamily(Artifakt.extraBold),
        fontSize = movie2cTextSize.h2,
        lineHeight = movie2cTextLineHeight.h2,
    ),
    h3 = TextStyle(
        fontFamily = FontFamily(Artifakt.extraBold),
        fontSize = movie2cTextSize.h3,
        lineHeight = movie2cTextLineHeight.h3,
    ),
    h4 = TextStyle(
        fontFamily = FontFamily(Artifakt.bold),
        fontSize = movie2cTextSize.h4,
        lineHeight = movie2cTextLineHeight.h4,
    ),
    h5 = TextStyle(
        fontFamily = FontFamily(Artifakt.bold),
        fontSize = movie2cTextSize.h5,
        lineHeight = movie2cTextLineHeight.h5,
    ),
    h6 = TextStyle(
        fontFamily = FontFamily(Artifakt.bold),
        fontSize = movie2cTextSize.h6,
        lineHeight = movie2cTextLineHeight.h6,
    ),
    h7 = TextStyle(
        fontFamily = FontFamily(Artifakt.extraBold),
        fontSize = movie2cTextSize.h7,
        lineHeight = movie2cTextLineHeight.h7,
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(Artifakt.normal),
        fontSize = movie2cTextSize.body1,
        lineHeight = movie2cTextLineHeight.body1,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Artifakt.normal),
        fontSize = movie2cTextSize.body2,
        lineHeight = movie2cTextLineHeight.body2,
    ),
    body3 = TextStyle(
        fontFamily = FontFamily(Artifakt.normal),
        fontSize = movie2cTextSize.body3,
        lineHeight = movie2cTextLineHeight.body3,
    ),
    body4 = TextStyle(
        fontFamily = FontFamily(Artifakt.normal),
        fontSize = movie2cTextSize.body4,
        lineHeight = movie2cTextLineHeight.body4,
    ),
    body5 = TextStyle(
        fontFamily = FontFamily(Artifakt.medium),
        fontSize = movie2cTextSize.body5,
        lineHeight = movie2cTextLineHeight.body5,
    ),
    label = TextStyle(
        fontFamily = FontFamily(Artifakt.semiBold),
        fontSize = movie2cTextSize.label,
        lineHeight = movie2cTextLineHeight.label,
    ),
    button = TextStyle(
        fontFamily = FontFamily(Artifakt.extraBold),
        fontSize = movie2cTextSize.button,
        lineHeight = movie2cTextLineHeight.button,
    ),
    quotes = TextStyle(
        fontFamily = FontFamily(Artifakt.lightItalic),
        fontSize = movie2cTextSize.quotes,
        lineHeight = movie2cTextLineHeight.quotes,
    ),
    bodyLight = TextStyle(
        fontFamily = FontFamily(Artifakt.extraLight),
        fontSize = movie2cTextSize.bodyLight,
        lineHeight = movie2cTextLineHeight.bodyLight
    )
)

internal val LocalMovies2cTypography =
    staticCompositionLocalOf<Movies2cTypography> { error("No Movies2cTypography provided") }