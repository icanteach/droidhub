package co.icanteach.apps.android.droidhub.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import co.icanteach.apps.android.droidhub.design.darkColors
import co.icanteach.apps.android.droidhub.design.lightColors

@Composable
fun DroidhubTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = getColors(isSystemInDarkTheme),
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}

internal fun getColors(isSystemInDarkTheme: Boolean): Colors {
    return if (isSystemInDarkTheme) darkColors else lightColors
}