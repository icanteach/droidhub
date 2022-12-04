package co.icanteach.apps.android.droidhub.features.bookmark.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.icanteach.apps.android.droidhub.components.core.ArticleComponentItem
import co.icanteach.apps.android.droidhub.components.core.ComponentItem
import co.icanteach.apps.android.droidhub.components.core.PodcastComponentItem
import co.icanteach.apps.android.droidhub.components.core.VideoComponentItem
import co.icanteach.apps.android.droidhub.components.items.ArticleComponent
import co.icanteach.apps.android.droidhub.components.items.PodcastComponent
import co.icanteach.apps.android.droidhub.components.items.VideoComponent
import co.icanteach.apps.android.droidhub.features.bookmark.BookmarkScreenUiState
import co.icanteach.apps.android.droidhub.features.feed.FilterChip

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