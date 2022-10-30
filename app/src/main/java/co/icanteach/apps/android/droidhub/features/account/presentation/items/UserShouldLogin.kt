package co.icanteach.apps.android.droidhub.features.account.presentation.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.features.account.presentation.composables.SingleItem

@Composable
fun UserShouldLogin(
    onAuthScreenNavigated: () -> (Unit)
) {
    SingleItem(
        title = stringResource(id = R.string.account_need_to_login_title),
        description = stringResource(id = R.string.account_need_to_login_desc),
        icon = painterResource(id = R.drawable.ic_login)
    ) {
        onAuthScreenNavigated()
    }
    VerticalSpacer(value = 32.dp)
}