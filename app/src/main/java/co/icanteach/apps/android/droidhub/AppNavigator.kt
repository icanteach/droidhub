package co.icanteach.apps.android.droidhub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.icanteach.apps.android.droidhub.features.account.AccountScreen
import co.icanteach.apps.android.droidhub.features.feed.FeedScreen

@Composable
fun AppNavigator(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = Screens.FeedScreen.route
    ) {
        composable(Screens.FeedScreen.route) {
            FeedScreen()
        }

        composable(Screens.BookmarkScreen.route) {}

        composable(Screens.AccountScreen.route) {
            AccountScreen()
        }
    }
}

sealed class Screens(val route: String) {
    object FeedScreen : Screens("feed_screen")
    object BookmarkScreen : Screens("bookmark_screen")
    object AccountScreen : Screens("account_screen")
}