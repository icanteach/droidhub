package co.icanteach.apps.android.droidhub.features.account.presentation.items

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.account.presentation.composables.SwitchableItem

@Composable
fun DarkModeApp(
    isDarkThemeSelected: Boolean, onDarkThemeChanged: (Boolean) -> (Unit)
) {
    SwitchableItem(
        title = stringResource(id = R.string.account_dark_mode_title),
        description = stringResource(id = R.string.account_dark_mode_desc),
        icon = painterResource(id = R.drawable.ic_dark_mode),
        isChecked = isDarkThemeSelected,
    ) { result ->
        onDarkThemeChanged.invoke(result)
    }
    VerticalSpacer(value = 32.dp)
}

@Preview
@Composable
private fun DarkModeApp_Preview() {
    DroidhubTheme {
        Surface {
            DarkModeApp(isDarkThemeSelected = false) {}
        }
    }
}

@Preview
@Composable
private fun DarkModeAppSelected_Preview() {
    DroidhubTheme {
        Surface {
            DarkModeApp(isDarkThemeSelected = true) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkModeApp_DarkPreview() {
    DroidhubTheme {
        Surface {
            DarkModeApp(isDarkThemeSelected = false) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkModeAppSelected_DarkPreview() {
    DroidhubTheme {
        Surface {
            DarkModeApp(isDarkThemeSelected = true) {}
        }
    }
}