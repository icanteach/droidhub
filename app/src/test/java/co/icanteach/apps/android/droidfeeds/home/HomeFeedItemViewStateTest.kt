package co.icanteach.apps.android.droidfeeds.home

import co.icanteach.apps.android.droidfeeds.FeedItemFactory
import com.google.common.truth.Truth
import org.junit.Test


class HomeFeedItemViewStateTest {

    @Test
    fun `given category info as provided, then shouldShowCategoryInfo should return true`() {

        // Given
        val feedItem = FeedItemFactory.createFeedItem()
        val viewState = HomeFeedItemViewState(feedItem)

        // When
        val actualResult = viewState.shouldShowCategoryInfo()

        // Then
        Truth.assertThat(actualResult).isTrue()
    }

    @Test
    fun `given category info as empty, then shouldShowCategoryInfo should return false`() {

        // Given
        val feedItem = FeedItemFactory.createFeedItem(category = "")
        val viewState = HomeFeedItemViewState(feedItem)

        // When
        val actualResult = viewState.shouldShowCategoryInfo()

        // Then
        Truth.assertThat(actualResult).isFalse()
    }
}