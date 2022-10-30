package co.icanteach.apps.android.droidhub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.icanteach.apps.android.droidhub.features.account.presentation.AccountScreen
import co.icanteach.apps.android.droidhub.features.auth.AuthScreen
import co.icanteach.apps.android.droidhub.features.feed.FeedScreen
import co.icanteach.apps.android.droidhub.features.interests.InterestsScreen

@Composable
fun AppNavigator(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = Screens.FeedScreen.route
    ) {
        composable(Screens.FeedScreen.route) {
            FeedScreen()
        }

        composable(Screens.BookmarkScreen.route) {}
        composable(Screens.AuthScreen.route) {
            AuthScreen()
        }
        composable(Screens.AccountScreen.route) {
            AccountScreen(navController = navController)
        }

        composable(Screens.InterestsScreen.route) {
            InterestsScreen()
        }
    }
}

sealed class Screens(val route: String) {
    object FeedScreen : Screens("feed_screen")
    object BookmarkScreen : Screens("bookmark_screen")
    object AccountScreen : Screens("account_screen")
    object AuthScreen : Screens("auth_screen")
    object InterestsScreen : Screens("interests_screen")
}