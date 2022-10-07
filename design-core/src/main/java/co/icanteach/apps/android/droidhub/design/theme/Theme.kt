package co.icanteach.apps.android.droidhub.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DroidhubTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    SetSystemBarColors(isSystemInDarkTheme)

    MaterialTheme(
        colors = getColors(isSystemInDarkTheme),
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}

@Composable
fun SetSystemBarColors(isSystemInDarkTheme: Boolean) {
    val view = LocalView.current

    if (!view.isInEditMode) {

        val systemUiController = rememberSystemUiController()

        SideEffect {
            systemUiController.setStatusBarColor(
                color = if (isSystemInDarkTheme) Color.DarkGray else purple700
            )
        }
    }
}

internal fun getColors(isSystemInDarkTheme: Boolean): Colors {
    return if (isSystemInDarkTheme) DarkColorPalette else LightColorPalette
}