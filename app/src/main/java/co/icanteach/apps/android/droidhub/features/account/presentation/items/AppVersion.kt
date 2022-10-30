package co.icanteach.apps.android.droidhub.features.account.presentation.items

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.icanteach.apps.android.droidhub.BuildConfig
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.account.presentation.composables.SingleItem

@Composable
fun AppVersion() {
    SingleItem(
        title = stringResource(id = R.string.account_app_version_title),
        description = stringResource(
            id = R.string.account_app_version_desc, BuildConfig.VERSION_NAME
        ),
        icon = painterResource(id = R.drawable.ic_version)
    )
}

@Preview
@Composable
private fun AppVersion_Preview() {
    DroidhubTheme {
        Surface {
            AppVersion()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppVersion_DarkPreview() {
    DroidhubTheme {
        Surface {
            AppVersion()
        }
    }
}