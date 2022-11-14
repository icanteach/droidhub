package co.icanteach.apps.android.droidhub.features.feed.domain

import co.icanteach.apps.android.droidhub.features.feedcomponents.ArticleComponentItem
import co.icanteach.apps.android.droidhub.features.feedcomponents.ComponentItem
import co.icanteach.apps.android.droidhub.features.feedcomponents.PodcastComponentItem
import co.icanteach.apps.android.droidhub.features.feedcomponents.VideoComponentItem
import java.util.*
import javax.inject.Inject

class FeedComponentMapper @Inject constructor(
) {

    fun mapTo(response: List<FeedItemResponse>): List<ComponentItem> {
        return response.mapNotNull { item ->
            when (item.type) {
                "Article" -> {
                    ArticleComponentItem(
                        id = UUID.randomUUID().toString(),
                        category = item.category,
                        title = item.title,
                        desc = item.description,
                        imageUrl = item.image,
                        sharedBy = item.sharedBy,
                    )
                }
                "Podcast" -> {
                    PodcastComponentItem(
                        id = UUID.randomUUID().toString(),
                        category = item.category,
                        title = item.title,
                        source = item.source
                    )
                }
                "Video" -> {
                    VideoComponentItem(
                        id = UUID.randomUUID().toString(),
                        category = item.category,
                        title = item.title,
                        imageUrl = item.image,
                        sharedBy = item.sharedBy,
                        source = item.source
                    )
                }
                else -> {
                    null
                }
            }
        }
    }
}