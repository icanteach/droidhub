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
import co.icanteach.apps.android.droidhub.features.account.presentation.composables.SingleItem

@Composable
fun ShareTheApp() {
    SingleItem(
        title = stringResource(id = R.string.account_share_this_application_title),
        description = stringResource(id = R.string.account_share_this_application_desc),
        icon = painterResource(id = R.drawable.ic_share)
    ) {
        // TODO : https://github.com/icanteach/droidhub/issues/17
    }

    VerticalSpacer(value = 32.dp)
}

@Preview
@Composable
fun ShareTheApp_Preview() {
    DroidhubTheme {
        Surface {
            ShareTheApp()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShareTheApp_DarkPreview() {
    DroidhubTheme {
        Surface {
            ShareTheApp()
        }
    }
}