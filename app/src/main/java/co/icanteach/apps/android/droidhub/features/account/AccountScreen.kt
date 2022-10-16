package co.icanteach.apps.android.droidhub.features.account

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import co.icanteach.apps.android.droidhub.BuildConfig
import co.icanteach.apps.android.droidhub.R
import co.icanteach.apps.android.droidhub.Screens
import co.icanteach.apps.android.droidhub.design.composables.VerticalSpacer
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.account.composables.SingleItem
import co.icanteach.apps.android.droidhub.features.account.composables.SwitchableItem

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel = hiltViewModel(), navController: NavHostController
) {

    val scrollableState = rememberScrollState()
    val screenState = accountViewModel.accountScreenState

    AccountScreenContent(scrollableState = scrollableState,
        screenUiState = screenState,
        onDarkModeChanged = { event ->
            accountViewModel.onEvent(event)
        },
        onAuthScreenNavigated = {
            navController.navigate(Screens.AuthScreen.route)
        })
}

@Composable
fun AccountScreenContent(
    scrollableState: ScrollState,
    screenUiState: AccountScreenUiState,
    onDarkModeChanged: (AccountScreenEvent) -> Unit,
    onAuthScreenNavigated: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollableState)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SingleItem(
            title = stringResource(id = R.string.account_need_to_login_title),
            description = stringResource(id = R.string.account_need_to_login_desc),
            icon = painterResource(id = R.drawable.ic_login)
        ) {
            onAuthScreenNavigated()
        }

        VerticalSpacer(value = 32.dp)

        /**
         * TODO : open Interest Selection Dialog.
         * https://github.com/icanteach/droidhub/issues/16
         */
        SingleItem(
            title = stringResource(id = R.string.account_interested_in_title),
            description = stringResource(id = R.string.account_interested_in_desc),
            icon = painterResource(id = R.drawable.ic_topics)
        ) {
            /**
             * TODO : open Interest Selection Dialog.
             * https://github.com/icanteach/droidhub/issues/16
             */
        }

        VerticalSpacer(value = 32.dp)

        /**
         * TODO : https://github.com/icanteach/droidhub/issues/20
         */
        SingleItem(
            title = stringResource(id = R.string.account_report_a_problem_title),
            description = stringResource(id = R.string.account_report_a_problem_desc),
            icon = painterResource(id = R.drawable.ic_report_problem)
        ) {
            /**
             * TODO : https://github.com/icanteach/droidhub/issues/20
             */
        }

        VerticalSpacer(value = 32.dp)

        SingleItem(
            title = stringResource(id = R.string.account_rate_us_title),
            description = stringResource(id = R.string.account_rate_us_desc),
            icon = painterResource(id = R.drawable.ic_rate)
        ) {
            // TODO : https://github.com/icanteach/droidhub/issues/18
        }

        VerticalSpacer(value = 32.dp)

        SingleItem(
            title = stringResource(id = R.string.account_share_this_application_title),
            description = stringResource(id = R.string.account_share_this_application_desc),
            icon = painterResource(id = R.drawable.ic_share)
        ) {
            // TODO : https://github.com/icanteach/droidhub/issues/17
        }

        VerticalSpacer(value = 32.dp)

        SwitchableItem(
            title = stringResource(id = R.string.account_dark_mode_title),
            description = stringResource(id = R.string.account_dark_mode_desc),
            icon = painterResource(id = R.drawable.ic_dark_mode),
            isChecked = screenUiState.isDarkThemeSelected,
        ) { result ->
            onDarkModeChanged.invoke(AccountScreenEvent.OnDarkThemeChanged(result))
        }

        VerticalSpacer(value = 32.dp)

        SingleItem(
            title = stringResource(id = R.string.account_app_version_title),
            description = stringResource(
                id = R.string.account_app_version_desc, BuildConfig.VERSION_NAME
            ),
            icon = painterResource(id = R.drawable.ic_version)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AccountScreenDarkModeNotSelected_Preview() {
    AccountScreen_Preview(AccountScreenUiState(isDarkThemeSelected = false))
}

@Preview(showSystemUi = true)
@Composable
fun AccountScreenDarkModeSelected_Preview() {
    DroidhubTheme {
        Surface {
            AccountScreen_Preview(AccountScreenUiState(isDarkThemeSelected = true))
        }
    }
}

@Composable
fun AccountScreen_Preview(screenUiState: AccountScreenUiState) {
    val scrollableState = rememberScrollState()
    AccountScreenContent(
        scrollableState,
        screenUiState,
        onDarkModeChanged = {},
        onAuthScreenNavigated = {},

        )
}