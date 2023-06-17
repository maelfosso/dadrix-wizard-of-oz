package com.stockinos.mobile.wizardofoz.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.TextUnit

@Composable
fun primaryTextStyle(
    color: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = Size.textPrimarySizeGlobal,
    fontWeight: FontWeight? = Style.fontWeightPrimaryGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    // fontFamily: FontFamily? = FontFamily
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified
): TextStyle {
    return TextStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        background = background,
        textAlign = textAlign,
        textDecoration = textDecoration,
        textDirection = textDirection,
        lineHeight = lineHeight
    )
}
@Composable
fun boldTextStyle(
    color: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = Size.textBoldSizeGlobal,
    fontWeight: FontWeight? = Style.fontWeightBoldGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    // fontFamily: FontFamily? = FontFamily
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified
): TextStyle {
    return TextStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        background = background,
        textAlign = textAlign,
        textDecoration = textDecoration,
        textDirection = textDirection,
        lineHeight = lineHeight
    )
}


@Composable
fun secondaryTextStyle(
    color: Color = MaterialTheme.colorScheme.secondary,
    fontSize: TextUnit = Size.textSecondarySizeGlobal,
    fontWeight: FontWeight? = Style.fontWeightSecondaryGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    // fontFamily: FontFamily? = FontFamily
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified
): TextStyle {
    return TextStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        background = background,
        textAlign = textAlign,
        textDecoration = textDecoration,
        textDirection = textDirection,
        lineHeight = lineHeight
    )
}

@Composable
fun errorTextStyle(
    color: Color = Color.Red,
    fontSize: TextUnit = Size.textSecondarySizeGlobal,
    fontWeight: FontWeight? = Style.fontWeightSecondaryGlobal,
    fontStyle: FontStyle? = FontStyle.Normal,
    // fontFamily: FontFamily? = FontFamily
    letterSpacing: TextUnit = TextUnit.Unspecified,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    textAlign: TextAlign? = TextAlign.Start,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit = TextUnit.Unspecified
): TextStyle {
    return TextStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
        background = background,
        textAlign = textAlign,
        textDecoration = textDecoration,
        textDirection = textDirection,
        lineHeight = lineHeight
    )
}
