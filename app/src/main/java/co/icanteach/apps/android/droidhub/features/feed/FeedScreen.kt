package co.icanteach.apps.android.droidhub.features.feed

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.icanteach.apps.android.droidhub.design.theme.DroidhubTheme
import co.icanteach.apps.android.droidhub.features.feed.domain.FakeFilterItemsProvider
import co.icanteach.apps.android.droidhub.features.feedcomponents.*


@Composable
fun FeedScreen(
    feedViewModel: FeedViewModel = hiltViewModel(),
) {
    val screenState = feedViewModel.feedScreenState
    FeedScreen(
        screenState,
        onSelectedFilterChanged = { selectedFilter ->
            feedViewModel.onSelectedFilterChanged(selectedFilter)
        })
}

@Composable
fun FeedScreen(
    screenState: FeedScreenUiState,
    onSelectedFilterChanged: (String) -> (Unit)
) {

    val scrollState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth(),
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
                )
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
fun FeedScreen_Preview() {
    val filters = FakeFilterItemsProvider().filters
    val articleComponentItem = ComponentFactory.createArticleComponentItem()
    val items = mutableListOf<ComponentItem>(articleComponentItem, articleComponentItem)
    FeedScreen(FeedScreenUiState(components = items, filters = filters)) {}
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FeedScreen_DarkModePreview() {
    val filters = FakeFilterItemsProvider().filters
    val articleComponentItem = ComponentFactory.createArticleComponentItem()
    val items = mutableListOf<ComponentItem>(articleComponentItem, articleComponentItem)

    DroidhubTheme {
        Surface {
            FeedScreen(FeedScreenUiState(components = items, filters = filters)) {}
        }
    }
}