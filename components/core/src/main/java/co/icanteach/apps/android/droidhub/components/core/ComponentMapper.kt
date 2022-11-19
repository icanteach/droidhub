package co.icanteach.apps.android.droidhub.components.core

import javax.inject.Inject

class ComponentMapper @Inject constructor(
) {
    fun mapTo(
        response: List<ComponentItemResponse>, bookmarkIds: List<String> = emptyList()
    ): List<ComponentItem> {
        return response.mapNotNull { item ->
            mapTo(item, bookmarkIds.contains(item.id))
        }
    }

    fun mapTo(
        item: ComponentItemResponse, addedToBookmark: Boolean = false
    ): ComponentItem? {
        return when (item.type) {
            "Article" -> {
                ArticleComponentItem(
                    id = item.id,
                    category = item.category,
                    title = item.title,
                    desc = item.description,
                    imageUrl = item.image,
                    sharedBy = item.sharedBy,
                    addedToBookmark = addedToBookmark
                )
            }
            "Podcast" -> {
                PodcastComponentItem(
                    id = item.id,
                    category = item.category,
                    title = item.title,
                    source = item.source,
                    addedToBookmark = addedToBookmark
                )
            }
            "Video" -> {
                VideoComponentItem(
                    id = item.id,
                    category = item.category,
                    title = item.title,
                    imageUrl = item.image,
                    sharedBy = item.sharedBy,
                    source = item.source,
                    addedToBookmark = addedToBookmark
                )
            }
            else -> {
                null
            }
        }
    }
}