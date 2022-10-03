package co.icanteach.apps.android.droidhub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigator(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = Screens.FeedScreen.route
    ) {
        composable(Screens.FeedScreen.route) {}

        composable(Screens.BookmarkScreen.route) {}

        composable(Screens.AccountScreen.route) {}
    }
}

sealed class Screens(val route: String) {
    object FeedScreen : Screens("feed_screen")
    object BookmarkScreen : Screens("bookmark_screen")
    object AccountScreen : Screens("account_screen")
}