package co.icanteach.apps.android.droidhub.features.feed.domain

import co.icanteach.apps.android.droidhub.features.feedcomponents.ComponentFactory
import co.icanteach.apps.android.droidhub.features.feedcomponents.ComponentItem
import javax.inject.Inject

class FetchFeedUseCase @Inject constructor(
) {

    fun fetchFeed(): List<ComponentItem> {
        return mutableListOf(
            ComponentFactory.createArticleComponentItem(),
            ComponentFactory.createVideoComponentItem(),
            ComponentFactory.createArticleComponentItem(),
            ComponentFactory.createPodcastComponentItem(),
            ComponentFactory.createVideoComponentItem(),
            ComponentFactory.createArticleComponentItem(),
            ComponentFactory.createVideoComponentItem(),
        )
    }
}