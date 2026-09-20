package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = SafeCyan,
    onPrimary = SafeNavy,
    primaryContainer = SafeNavyLight,
    onPrimaryContainer = SafePrimaryContainer,
    secondary = SafeSecondary,
    onSecondary = DarkBackground,
    secondaryContainer = SafeNavyLight,
    onSecondaryContainer = SafeSecondaryContainer,
    tertiary = SafeGreen,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    onSurfaceVariant = DarkTextSecondary,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SafePrimary,
    onPrimary = LightSurface,
    primaryContainer = SafePrimaryContainer,
    onPrimaryContainer = SafeNavy,
    secondary = SafeSecondary,
    onSecondary = LightSurface,
    secondaryContainer = SafeSecondaryContainer,
    onSecondaryContainer = SafeNavy,
    tertiary = SafeTertiary,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    onSurfaceVariant = LightTextSecondary,
  )

@Composable
fun RotaSeguraTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  RotaSeguraTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}

