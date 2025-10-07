package com.neilsayok.bluelabs.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import bluelabscmp.composeapp.generated.resources.Res
import bluelabscmp.composeapp.generated.resources.poppins_black
import bluelabscmp.composeapp.generated.resources.poppins_black_italic
import bluelabscmp.composeapp.generated.resources.poppins_bold
import bluelabscmp.composeapp.generated.resources.poppins_bold_italic
import bluelabscmp.composeapp.generated.resources.poppins_extra_bold
import bluelabscmp.composeapp.generated.resources.poppins_extra_bold_italic
import bluelabscmp.composeapp.generated.resources.poppins_extra_light
import bluelabscmp.composeapp.generated.resources.poppins_extra_light_italic
import bluelabscmp.composeapp.generated.resources.poppins_italic
import bluelabscmp.composeapp.generated.resources.poppins_light
import bluelabscmp.composeapp.generated.resources.poppins_light_italic
import bluelabscmp.composeapp.generated.resources.poppins_medium
import bluelabscmp.composeapp.generated.resources.poppins_medium_italic
import bluelabscmp.composeapp.generated.resources.poppins_regular
import bluelabscmp.composeapp.generated.resources.poppins_semibold
import bluelabscmp.composeapp.generated.resources.poppins_semibold_italic
import bluelabscmp.composeapp.generated.resources.poppins_thin
import bluelabscmp.composeapp.generated.resources.poppins_thin_italic
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(InternalResourceApi::class)
@Composable
fun poppinsFontFamily(): FontFamily = FontFamily(
    Font(Res.font.poppins_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
    Font(Res.font.poppins_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(Res.font.poppins_extra_light, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
    Font(Res.font.poppins_extra_light_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(Res.font.poppins_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(Res.font.poppins_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(Res.font.poppins_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(Res.font.poppins_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(Res.font.poppins_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(Res.font.poppins_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(Res.font.poppins_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(Res.font.poppins_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(Res.font.poppins_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(Res.font.poppins_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(Res.font.poppins_extra_bold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(Res.font.poppins_extra_bold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(Res.font.poppins_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(Res.font.poppins_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
)

@Composable
fun PoppinsTypography() = MaterialTheme.typography.run {
    val fontFamily = poppinsFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}