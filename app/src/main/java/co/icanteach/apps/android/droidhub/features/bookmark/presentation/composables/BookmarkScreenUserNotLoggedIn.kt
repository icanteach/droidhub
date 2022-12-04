package co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables

import androidx.compose.runtime.Composable
import co.icanteach.apps.android.droidhub.features.auth.AuthScreen

@Composable
fun BookmarkScreenUserNotLoggedIn(
    onAuthScreenNavigated: () -> (Unit)
) {
    AuthScreen {
        onAuthScreenNavigated.invoke()
    }
}