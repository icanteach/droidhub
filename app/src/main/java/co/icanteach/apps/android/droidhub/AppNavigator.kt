package co.icanteach.apps.android.droidhub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import co.icanteach.apps.android.droidhub.features.account.presentation.AccountScreen
import co.icanteach.apps.android.droidhub.features.auth.AuthScreen
import co.icanteach.apps.android.droidhub.features.bookmark.BookmarkScreen
import co.icanteach.apps.android.droidhub.features.feed.FeedScreen
import co.icanteach.apps.android.droidhub.features.interests.InterestsScreen
import co.icanteach.apps.android.droidhub.features.submission.SubmissionScreen
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AppNavigator(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
) {
    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(
            navController = navController, startDestination = Screens.FeedScreen.route
        ) {
            composable(Screens.FeedScreen.route) {
                FeedScreen()
            }

            composable(Screens.BookmarkScreen.route) {
                BookmarkScreen()
            }
            composable(Screens.AuthScreen.route) {
                AuthScreen {
                    navController.navigateUp()
                }
            }
            composable(Screens.AccountScreen.route) {
                AccountScreen(
                    onAuthScreenNavigated = {
                        navController.navigate(Screens.AuthScreen.route)
                    }
                ) {
                    navController.navigate(Screens.InterestsScreen.route)
                }
            }

            composable(Screens.InterestsScreen.route) {
                InterestsScreen()
            }

            bottomSheet(
                route = Screens.SubmissionScreen.route
            ) {
                SubmissionScreen {
                    navController.navigateUp()
                }
            }

            bottomSheet(
                route = Screens.SubmissionScreen.route + "?incomingUrl={incomingUrl}",
                arguments = listOf(navArgument(
                    name = "incomingUrl"
                ) {
                    type = NavType.IntType
                })
            ) {
                SubmissionScreen {
                    navController.navigateUp()
                }
            }
        }
    }
}

sealed class Screens(val route: String) {
    object FeedScreen : Screens("feed_screen")
    object BookmarkScreen : Screens("bookmark_screen")
    object AccountScreen : Screens("account_screen")
    object AuthScreen : Screens("auth_screen")
    object InterestsScreen : Screens("interests_screen")
    object SubmissionScreen : Screens("submission_screen")
}