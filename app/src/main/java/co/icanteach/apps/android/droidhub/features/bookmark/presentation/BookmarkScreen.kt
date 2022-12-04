package co.icanteach.apps.android.droidhub.features.bookmark.presentation

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import co.icanteach.apps.android.droidhub.components.core.ComponentFactory
import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.bookmark.BookmarkScreenUiState
import co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables.BookmarkScreenEmptyState
import co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables.BookmarkScreenLoading
import co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables.BookmarkScreenSuccessResult
import co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables.BookmarkScreenUserNotLoggedIn
import co.icanteach.apps.android.droidhub.features.feed.domain.FakeFilterItemsProvider

@Composable
fun BookmarkScreen(
    screenViewModel: BookmarkScreenViewModel = hiltViewModel()
) {
    val screenState = screenViewModel.bookScreenUiState.collectAsState().value

    BookmarkScreen(screenState,
        onSelectedFilterChanged = { selectedFilter ->
            screenViewModel.onSelectedFilterChanged(selectedFilter)
        }, onBookmarkItemClicked = { component ->
            screenViewModel.onBookmarkItemClicked(component)
        }, onAuthScreenNavigated = {
            screenViewModel.refreshBookmarkContent()
        })
}

@Composable
fun BookmarkScreen(
    screenState: BookmarkScreenUiState,
    onSelectedFilterChanged: (String) -> (Unit),
    onBookmarkItemClicked: (ComponentItem) -> (Unit),
    onAuthScreenNavigated: () -> (Unit)
) {

    when (screenState) {
        is BookmarkScreenUiState.Error -> {

        }
        is BookmarkScreenUiState.Success -> {
            BookmarkScreenSuccessResult(
                screenState = screenState,
                onSelectedFilterChanged = {
                    onSelectedFilterChanged.invoke(it)
                }, onBookmarkItemClicked = {
                    onBookmarkItemClicked.invoke(it)
                })
        }
        BookmarkScreenUiState.Loading -> {
            BookmarkScreenLoading()
        }
        BookmarkScreenUiState.UserNotLoggedIn -> {
            BookmarkScreenUserNotLoggedIn {
                onAuthScreenNavigated.invoke()
            }
        }
        BookmarkScreenUiState.Empty -> {
            BookmarkScreenEmptyState()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookmarkScreenLoading_Preview() {
    BookmarkScreen_Preview(BookmarkScreenUiState.Loading)
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookmarkScreenLoading_DarkPreview() {
    DroidhubTheme {
        Surface {
            BookmarkScreen_Preview(BookmarkScreenUiState.Loading)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookmarkScreenEmpty_Preview() {
    BookmarkScreen_Preview(BookmarkScreenUiState.Empty)
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookmarkScreenEmpty_DarkPreview() {
    DroidhubTheme {
        Surface {
            BookmarkScreen_Preview(BookmarkScreenUiState.Empty)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookmarkScreenUserNotLoggedIn_Preview() {
    BookmarkScreen_Preview(BookmarkScreenUiState.UserNotLoggedIn)
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookmarkScreenUserNotLoggedIn_DarkPreview() {
    DroidhubTheme {
        Surface {
            BookmarkScreen_Preview(BookmarkScreenUiState.UserNotLoggedIn)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BookmarkScreenSuccess_Preview() {
    val filters = FakeFilterItemsProvider().filters
    val articleComponentItem = ComponentFactory.createArticleComponentItem()
    val items = mutableListOf<ComponentItem>(articleComponentItem, articleComponentItem)
    BookmarkScreen_Preview(
        BookmarkScreenUiState.Success(
            components = items,
            filters = filters
        )
    )
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookmarkScreenSuccess_DarkModePreview() {
    val filters = FakeFilterItemsProvider().filters
    val articleComponentItem = ComponentFactory.createArticleComponentItem()
    val items = mutableListOf<ComponentItem>(
        articleComponentItem, articleComponentItem
    )
    DroidhubTheme {
        Surface {
            BookmarkScreen_Preview(
                BookmarkScreenUiState.Success(
                    components = items,
                    filters = filters
                )
            )
        }
    }
}

@Composable
fun BookmarkScreen_Preview(screenUiState: BookmarkScreenUiState) {
    BookmarkScreen(screenUiState,
        onSelectedFilterChanged = {},
        onBookmarkItemClicked = {},
        onAuthScreenNavigated = {})
}