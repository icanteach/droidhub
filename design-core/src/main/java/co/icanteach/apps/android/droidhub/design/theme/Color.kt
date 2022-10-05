package co.icanteach.apps.android.droidhub.design.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val teal200 = Color(0xFF03DAC5)
val white = Color(0xFFFFFFFF)

internal val DarkColorPalette = darkColors(
    primary = purple200, primaryVariant = purple700, secondary = teal200
)

internal val LightColorPalette = lightColors(
    primary = purple500, primaryVariant = purple700, secondary = teal200

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)