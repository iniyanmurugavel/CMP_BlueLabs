package com.neilsayok.bluelabs.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val CODE_BLOCK_BACKGROUND_COLOR = Color(0xFF282A35)


// ColorTokens.kt

object ColorLightTokens {
    val Primary = Color(0xFF1E90FF)
    val OnPrimary = Color.White
    val PrimaryContainer = Color(0xFFD0E9FF)
    val OnPrimaryContainer = Color(0xFF002B45)
    val InversePrimary = Color(0xFF00BFFF)

    val Secondary = Color(0xFF87CEFA)
    val OnSecondary = Color.Black
    val SecondaryContainer = Color(0xFFDDF3FF)
    val OnSecondaryContainer = Color(0xFF002C3E)

    val Tertiary = Color(0xFF7D3C98) // Accent: links, badges
    val OnTertiary = Color.White
    val TertiaryContainer = Color(0xFFF1E1FF)
    val OnTertiaryContainer = Color(0xFF3D0056)

    val Background = Color(0xFFF0F8FF)
    val OnBackground = Color(0xFF2E2E2E)

    val Surface = Color(0xFFE3F2FD)
    val OnSurface = Color(0xFF2E2E2E)

    val SurfaceVariant = Color(0xFFD3D3D3)
    val OnSurfaceVariant = Color(0xFF3A3A3A)

    val InverseSurface = Color(0xFF2E2E2E)
    val InverseOnSurface = Color.White

    val Error = Color(0xFFFF7F50) // Notification banners
    val OnError = Color.White
    val ErrorContainer = Color(0xFFFFDAD4)
    val OnErrorContainer = Color(0xFF410001)

    val Outline = Color(0xFFB0B0B0)
    val OutlineVariant = Color(0xFFD3D3D3)

    val Scrim = Color(0x66000000)

    val SurfaceBright = Color(0xFFF7F9FB)
    val SurfaceDim = Color(0xFFE8EDF1)

    val SurfaceContainer = Color(0xFFE3F2FD)
    val SurfaceContainerLowest = Color(0xFFFFFFFF)
    val SurfaceContainerLow = Color(0xFFF2F9FF)
    val SurfaceContainerHigh = Color(0xFFD3E8FB)
    val SurfaceContainerHighest = Color(0xFFC2DEF7)
}

object ColorDarkTokens {
    val Primary = Color(0xFF1E90FF)
    val OnPrimary = Color.Black
    val PrimaryContainer = Color(0xFF004A77)
    val OnPrimaryContainer = Color(0xFFD0E9FF)
    val InversePrimary = Color(0xFF87CEFA)

    val Secondary = Color(0xFF00BFFF)
    val OnSecondary = Color.Black
    val SecondaryContainer = Color(0xFF00374C)
    val OnSecondaryContainer = Color(0xFFB5EBFF)

    val Tertiary = Color(0xFF8A2BE2)
    val OnTertiary = Color.White
    val TertiaryContainer = Color(0xFF3C006A)
    val OnTertiaryContainer = Color(0xFFE9D1FF)

    val Background = Color(0xFF121212)
    val OnBackground = Color(0xFFE0E0E0)

    val Surface = Color(0xFF1A1A2E)
    val OnSurface = Color(0xFFE0E0E0)

    val SurfaceVariant = Color(0xFFB0B0B0)
    val OnSurfaceVariant = Color(0xFF2A2A2A)

    val InverseSurface = Color(0xFFE0E0E0)
    val InverseOnSurface = Color(0xFF121212)

    val Error = Color(0xFFFF6347)
    val OnError = Color.Black
    val ErrorContainer = Color(0xFF93000A)
    val OnErrorContainer = Color(0xFFFFDAD4)

    val Outline = Color(0xFF888888)
    val OutlineVariant = Color(0xFF666666)

    val Scrim = Color(0x88000000)

    val SurfaceBright = Color(0xFF1F1F2F)
    val SurfaceDim = Color(0xFF10101A)

    val SurfaceContainer = Color(0xFF1A1A2E)
    val SurfaceContainerLowest = Color(0xFF0D0D14)
    val SurfaceContainerLow = Color(0xFF141421)
    val SurfaceContainerHigh = Color(0xFF232340)
    val SurfaceContainerHighest = Color(0xFF2E2E5A)
}

val LightColorScheme = lightColorScheme(
    primary = ColorLightTokens.Primary,
    onPrimary = ColorLightTokens.OnPrimary,
    primaryContainer = ColorLightTokens.PrimaryContainer,
    onPrimaryContainer = ColorLightTokens.OnPrimaryContainer,
    inversePrimary = ColorLightTokens.InversePrimary,
    secondary = ColorLightTokens.Secondary,
    onSecondary = ColorLightTokens.OnSecondary,
    secondaryContainer = ColorLightTokens.SecondaryContainer,
    onSecondaryContainer = ColorLightTokens.OnSecondaryContainer,
    tertiary = ColorLightTokens.Tertiary,
    onTertiary = ColorLightTokens.OnTertiary,
    tertiaryContainer = ColorLightTokens.TertiaryContainer,
    onTertiaryContainer = ColorLightTokens.OnTertiaryContainer,
    background = ColorLightTokens.Background,
    onBackground = ColorLightTokens.OnBackground,
    surface = ColorLightTokens.Surface,
    onSurface = ColorLightTokens.OnSurface,
    surfaceVariant = ColorLightTokens.SurfaceVariant,
    onSurfaceVariant = ColorLightTokens.OnSurfaceVariant,
    surfaceTint = ColorLightTokens.Primary,
    inverseSurface = ColorLightTokens.InverseSurface,
    inverseOnSurface = ColorLightTokens.InverseOnSurface,
    error = ColorLightTokens.Error,
    onError = ColorLightTokens.OnError,
    errorContainer = ColorLightTokens.ErrorContainer,
    onErrorContainer = ColorLightTokens.OnErrorContainer,
    outline = ColorLightTokens.Outline,
    outlineVariant = ColorLightTokens.OutlineVariant,
    scrim = ColorLightTokens.Scrim,
    surfaceBright = ColorLightTokens.SurfaceBright,
    surfaceDim = ColorLightTokens.SurfaceDim,
    surfaceContainer = ColorLightTokens.SurfaceContainer,
    surfaceContainerLowest = ColorLightTokens.SurfaceContainerLowest,
    surfaceContainerLow = ColorLightTokens.SurfaceContainerLow,
    surfaceContainerHigh = ColorLightTokens.SurfaceContainerHigh,
    surfaceContainerHighest = ColorLightTokens.SurfaceContainerHighest,
)

val DarkColorScheme = darkColorScheme(
    primary = ColorDarkTokens.Primary,
    onPrimary = ColorDarkTokens.OnPrimary,
    primaryContainer = ColorDarkTokens.PrimaryContainer,
    onPrimaryContainer = ColorDarkTokens.OnPrimaryContainer,
    inversePrimary = ColorDarkTokens.InversePrimary,
    secondary = ColorDarkTokens.Secondary,
    onSecondary = ColorDarkTokens.OnSecondary,
    secondaryContainer = ColorDarkTokens.SecondaryContainer,
    onSecondaryContainer = ColorDarkTokens.OnSecondaryContainer,
    tertiary = ColorDarkTokens.Tertiary,
    onTertiary = ColorDarkTokens.OnTertiary,
    tertiaryContainer = ColorDarkTokens.TertiaryContainer,
    onTertiaryContainer = ColorDarkTokens.OnTertiaryContainer,
    background = ColorDarkTokens.Background,
    onBackground = ColorDarkTokens.OnBackground,
    surface = ColorDarkTokens.Surface,
    onSurface = ColorDarkTokens.OnSurface,
    surfaceVariant = ColorDarkTokens.SurfaceVariant,
    onSurfaceVariant = ColorDarkTokens.OnSurfaceVariant,
    surfaceTint = ColorDarkTokens.Primary,
    inverseSurface = ColorDarkTokens.InverseSurface,
    inverseOnSurface = ColorDarkTokens.InverseOnSurface,
    error = ColorDarkTokens.Error,
    onError = ColorDarkTokens.OnError,
    errorContainer = ColorDarkTokens.ErrorContainer,
    onErrorContainer = ColorDarkTokens.OnErrorContainer,
    outline = ColorDarkTokens.Outline,
    outlineVariant = ColorDarkTokens.OutlineVariant,
    scrim = ColorDarkTokens.Scrim,
    surfaceBright = ColorDarkTokens.SurfaceBright,
    surfaceDim = ColorDarkTokens.SurfaceDim,
    surfaceContainer = ColorDarkTokens.SurfaceContainer,
    surfaceContainerLowest = ColorDarkTokens.SurfaceContainerLowest,
    surfaceContainerLow = ColorDarkTokens.SurfaceContainerLow,
    surfaceContainerHigh = ColorDarkTokens.SurfaceContainerHigh,
    surfaceContainerHighest = ColorDarkTokens.SurfaceContainerHighest,
)

