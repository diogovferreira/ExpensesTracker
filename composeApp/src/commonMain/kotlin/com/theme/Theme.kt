package org.dfcoding.theme

import BackgroundDark
import BackgroundLight
import ErrorDark
import ErrorLight
import OnBackgroundDark
import OnBackgroundLight
import OnErrorDark
import OnErrorLight
import OnPrimaryContainerDark
import OnPrimaryContainerLight
import OnPrimaryDark
import OnPrimaryLight
import OnSurfaceDark
import OnSurfaceLight
import OnSurfaceVariantDark
import OnSurfaceVariantLight
import PrimaryContainerDark
import PrimaryContainerLight
import PrimaryDark
import PrimaryLight
import SurfaceContainerDark
import SurfaceContainerLight
import SurfaceDark
import SurfaceLight
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    surfaceContainer = SurfaceContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceContainer = SurfaceContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
)

@Composable
internal fun CoinRoutineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val coinRoutineColorsPalette = if (darkTheme) DarkCoinRoutineColorsPalette else LightCoinRoutineColorsPalette

    CompositionLocalProvider(
        LocalCoinRoutineColorsPalette provides coinRoutineColorsPalette
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            shapes = Shapes(),
            content = content
        )
    }
}