package co.icanteach.apps.android.droidhub.features.account.presentation.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.features.account.domain.User
import co.icanteach.apps.android.droidhub.features.account.presentation.composables.SingleItem

@Composable
fun UserProfileInfo(
    user: User
) {
    SingleItem(
        title = user.name,
        description = user.email,
        icon = painterResource(id = R.drawable.ic_person)
    ) {}

    VerticalSpacer(value = 32.dp)
}