package co.icanteach.apps.android.droidhub.features.feed

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
import co.icanteach.apps.android.droidhub.features.feed.domain.FakeFilterItemsProvider


@Composable
fun FeedScreen(
    feedViewModel: FeedViewModel = hiltViewModel(),
) {
    val screenState = feedViewModel.feedScreenState.collectAsState().value

    FeedScreen(screenState, onSelectedFilterChanged = { selectedFilter ->
        feedViewModel.onSelectedFilterChanged(selectedFilter)
    }, onBookmarkItemClicked = { component ->
        feedViewModel.onBookmarkItemClicked(component)
    })
}

@Composable
fun FeedScreen(
    screenState: FeedScreenUiState,
    onSelectedFilterChanged: (String) -> (Unit),
    onBookmarkItemClicked: (ComponentItem) -> (Unit)
) {

    when (screenState) {
        FeedScreenUiState.Empty -> {

        }
        is FeedScreenUiState.Error -> {

        }
        is FeedScreenUiState.Success -> {
            FeedScreenResultScreen(screenState = screenState, onSelectedFilterChanged = {
                onSelectedFilterChanged.invoke(it)
            }, onBookmarkItemClicked = {
                onBookmarkItemClicked.invoke(it)
            })
        }
        FeedScreenUiState.Loading -> {
            FeedScreenLoadingScreen()
        }
    }
}

@Composable
fun FeedScreenLoadingScreen(
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
fun FeedScreenResultScreen(
    screenState: FeedScreenUiState.Success,
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
fun FeedScreenLoading_Preview() {
    FeedScreen(FeedScreenUiState.Loading, {}, {})
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FeedScreenLoading_DarkPreview() {
    DroidhubTheme {
        Surface {
            FeedScreen(FeedScreenUiState.Loading, {}, {})
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FeedScreenSuccess_Preview() {
    val filters = FakeFilterItemsProvider().filters
    val articleComponentItem = ComponentFactory.createArticleComponentItem()
    val items = mutableListOf<ComponentItem>(articleComponentItem, articleComponentItem)
    FeedScreen(FeedScreenUiState.Success(components = items, filters = filters), {}, {})
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FeedScreenSuccess_DarkModePreview() {
    val filters = FakeFilterItemsProvider().filters
    val articleComponentItem = ComponentFactory.createArticleComponentItem()
    val items = mutableListOf<ComponentItem>(
        articleComponentItem, articleComponentItem
    )
    DroidhubTheme {
        Surface {
            FeedScreen(FeedScreenUiState.Success(components = items, filters = filters), {}, {})
        }
    }
}