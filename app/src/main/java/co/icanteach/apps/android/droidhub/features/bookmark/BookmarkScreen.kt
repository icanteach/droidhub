package co.icanteach.apps.android.droidhub.features.bookmark

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.icanteach.apps.android.droidhub.components.core.*
import co.icanteach.apps.android.droidhub.components.items.ArticleComponent
import co.icanteach.apps.android.droidhub.components.items.PodcastComponent
import co.icanteach.apps.android.droidhub.components.items.VideoComponent
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.auth.AuthScreen
import co.icanteach.apps.android.droidhub.features.feed.FilterChip
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
    }
}

@Composable
fun BookmarkScreenLoading(
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun BookmarkScreenUserNotLoggedIn(
    onAuthScreenNavigated: () -> (Unit)
) {
    AuthScreen {
        onAuthScreenNavigated.invoke()
    }
}

@Composable
fun BookmarkScreenSuccessResult(
    screenState: BookmarkScreenUiState.Success,
    onSelectedFilterChanged: (String) -> (Unit),
    onBookmarkItemClicked: (ComponentItem) -> (Unit)
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            LazyRow(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                state = scrollState,
            ) {
                items(screenState.filters) { filterItem ->
                    FilterChip(
                        item = filterItem,
                        onSelectedCategoryChanged = { selectedFilter ->
                            onSelectedFilterChanged(selectedFilter)
                        },
                    )
                }
            }
        }

        items(screenState.components) { item ->
            when (item) {
                is ArticleComponentItem -> ArticleComponent(
                    item = item
                ) {
                    onBookmarkItemClicked.invoke(item)
                }
                is VideoComponentItem -> VideoComponent(
                    item = item
                )

                is PodcastComponentItem -> PodcastComponent(
                    item = item
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BookmarkScreenLoading_Preview() {
    BookmarkScreen_Preview(BookmarkScreenUiState.Loading)
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BookmarkUserNotLoggedIn_Preview() {
    BookmarkScreen_Preview(BookmarkScreenUiState.UserNotLoggedIn)
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