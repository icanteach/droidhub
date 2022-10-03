package co.icanteach.apps.android.droidhub

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    BottomNavigation {

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        BottomNavigationItem.items.forEach { navItem ->
            BottomNavigationItem(selected = currentRoute == navItem.route, icon = {
                Icon(
                    painter = painterResource(id = navItem.icon),
                    modifier = Modifier.size(22.dp),
                    contentDescription = stringResource(id = navItem.title)
                )
            }, label = {
                Text(text = stringResource(id = navItem.title))
            }, onClick = {
                navController.navigate(navItem.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
    }
}

sealed class BottomNavigationItem(
    @StringRes val title: Int, @DrawableRes val icon: Int, val route: String
) {
    object Home : BottomNavigationItem(
        R.string.nav_home_title, R.drawable.ic_home, Screens.FeedScreen.route
    )

    object Bookmarks : BottomNavigationItem(
        R.string.nav_home_bookmarks, R.drawable.ic_bookmark, Screens.BookmarkScreen.route
    )

    object Account : BottomNavigationItem(
        R.string.nav_home_account, R.drawable.ic_account, Screens.AccountScreen.route
    )

    companion object {
        val items = listOf(Home, Bookmarks, Account)
    }
}