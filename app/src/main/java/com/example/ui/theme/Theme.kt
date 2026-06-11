package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = HeritageRed,
    secondary = RidgeGold,
    tertiary = FoundryCream,
    background = CementLight,
    surface = CementDeep, // CementDeep or starkWhite
    onPrimary = StarkWhite,
    onSecondary = IndustrialBlack,
    onTertiary = IndustrialBlack,
    onBackground = IndustrialBlack,
    onSurface = IndustrialBlack
)

private val DarkColorScheme = darkColorScheme(
    primary = HeritageRed,
    secondary = RidgeGold,
    tertiary = FoundryCream,
    background = IndustrialBlack,
    surface = SlateGrey,
    onPrimary = StarkWhite,
    onSecondary = StarkWhite,
    onTertiary = StarkWhite,
    onBackground = StarkWhite,
    onSurface = StarkWhite
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We intentionally force our custom branding colors to ensure the Historic Industrial vibe
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
