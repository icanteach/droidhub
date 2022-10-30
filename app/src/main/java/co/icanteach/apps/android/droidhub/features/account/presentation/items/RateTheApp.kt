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
fun RateTheApp() {
    SingleItem(
        title = stringResource(id = R.string.account_rate_us_title),
        description = stringResource(id = R.string.account_rate_us_desc),
        icon = painterResource(id = R.drawable.ic_rate)
    ) {
        // TODO : https://github.com/icanteach/droidhub/issues/18
    }

    VerticalSpacer(value = 32.dp)
}

@Preview
@Composable
fun RateTheApp_Preview() {
    DroidhubTheme {
        Surface {
            RateTheApp()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RateTheApp_DarkPreview() {
    DroidhubTheme {
        Surface {
            RateTheApp()
        }
    }
}