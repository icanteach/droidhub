package co.icanteach.apps.android.droidhub.features.feed.domain

import co.icanteach.apps.android.droidhub.features.feedcomponents.ComponentFactory
import co.icanteach.apps.android.droidhub.features.feedcomponents.ComponentItem
import javax.inject.Inject

class FetchDroidhubMainFeedUseCase @Inject constructor() {

    fun fetchMainFeed(): List<ComponentItem> {
        return mutableListOf(
            ComponentFactory.createPodcastComponentItem(),
            ComponentFactory.createArticleComponentItem(),
            ComponentFactory.createVideoComponentItem(),
            ComponentFactory.createArticleComponentItem(),
            ComponentFactory.createVideoComponentItem(),
            ComponentFactory.createArticleComponentItem(),
            ComponentFactory.createVideoComponentItem(),
        )
    }

}