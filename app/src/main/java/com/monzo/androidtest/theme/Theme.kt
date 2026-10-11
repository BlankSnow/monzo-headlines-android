package com.monzo.androidtest.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun GuardianTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColors,
        content = content
    )
}

private val LightColors = lightColors(
    primary = LightPrimary,
    secondary = LightSecondary,
    surface = LightSurface,
)
