package co.icanteach.apps.android.droidhub.features.account.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import co.icanteach.apps.android.droidhub.Screens
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.account.domain.User
import co.icanteach.apps.android.droidhub.features.account.presentation.items.*

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
        },
        onInterestsScreenNavigated = {
            navController.navigate(Screens.InterestsScreen.route)
        })
}

@Composable
fun AccountScreenContent(
    scrollableState: ScrollState,
    screenUiState: AccountScreenUiState,
    onDarkModeChanged: (AccountScreenEvent) -> Unit,
    onAuthScreenNavigated: () -> Unit,
    onInterestsScreenNavigated: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollableState)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (screenUiState.isUserLoggedIn) {
            UserProfileInfo(screenUiState.user)
        } else {
            UserShouldLogin {
                onAuthScreenNavigated.invoke()
            }
        }

        InterestedIn {
            onInterestsScreenNavigated.invoke()
        }
        ReportAProblem()
        RateTheApp()
        ShareTheApp()
        DarkModeApp(isDarkThemeSelected = screenUiState.isDarkThemeSelected) { result ->
            onDarkModeChanged.invoke(AccountScreenEvent.OnDarkThemeChanged(result))
        }
        AppVersion()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AccountScreenLoggedInUserStateSelected_Preview() {
    val user = User(name = "Murat Can BUR", email = "muratcanbur@gmail.com")
    AccountScreen_Preview(createAccountScreenUiState(isUserLoggedIn = true, user = user))
}

@Preview(showSystemUi = true)
@Composable
fun AccountScreenDarkModeNotSelected_Preview() {
    AccountScreen_Preview(createAccountScreenUiState(isDarkThemeSelected = false))
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AccountScreenDarkModeNotSelected_DarkPreview() {
    DroidhubTheme {
        Surface {
            AccountScreen_Preview(
                createAccountScreenUiState(isDarkThemeSelected = false)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AccountScreenDarkModeSelected_Preview() {
    Surface {
        AccountScreen_Preview(createAccountScreenUiState(isDarkThemeSelected = true))
    }
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AccountScreenDarkModeSelected_DarkPreview() {
    DroidhubTheme {
        Surface {
            AccountScreen_Preview(createAccountScreenUiState(isDarkThemeSelected = true))
        }
    }
}

private fun createAccountScreenUiState(
    isDarkThemeSelected: Boolean = false, isUserLoggedIn: Boolean = false, user: User = User()
): AccountScreenUiState {
    return AccountScreenUiState(
        isDarkThemeSelected = isDarkThemeSelected, isUserLoggedIn = isUserLoggedIn, user = user
    )
}

@Composable
fun AccountScreen_Preview(screenUiState: AccountScreenUiState) {
    val scrollableState = rememberScrollState()
    AccountScreenContent(scrollableState,
        screenUiState,
        onDarkModeChanged = {},
        onAuthScreenNavigated = {},
        onInterestsScreenNavigated = {})
}