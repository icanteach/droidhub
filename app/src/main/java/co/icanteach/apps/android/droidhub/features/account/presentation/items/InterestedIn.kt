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
fun InterestedIn(
    onInterestsScreenNavigated: () -> Unit
) {
    SingleItem(
        title = stringResource(id = R.string.account_interested_in_title),
        description = stringResource(id = R.string.account_interested_in_desc),
        icon = painterResource(id = R.drawable.ic_topics)
    ) {
        onInterestsScreenNavigated.invoke()
    }

    VerticalSpacer(value = 32.dp)
}

@Preview
@Composable
private fun InterestedIn_Preview() {
    DroidhubTheme {
        Surface {
            InterestedIn {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InterestedIn_DarkPreview() {
    DroidhubTheme {
        Surface {
            InterestedIn() {}
        }
    }
}