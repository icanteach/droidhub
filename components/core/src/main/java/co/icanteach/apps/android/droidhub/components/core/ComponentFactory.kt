package co.icanteach.apps.android.droidhub.components.core

object ComponentFactory {

    fun createArticleComponentItem(
        id: String = "123e4567-e89b-12d3-a456-426614174000",
        title: String = "A pragmatic guide to Hilt with Kotlin",
        desc: String = "An easy way to use dependency injection in your Android app",
        category: String = "Dagger Hilt",
        imageUrl: String = "https://miro.medium.com/max/1400/1*9cp9m4LO4zkpgv2s30Cg9A.png",
        sharedBy: String = "Droidhub",
        addedToBookmark: Boolean = false
    ): ArticleComponentItem {
        return ArticleComponentItem(
            id = id,
            title = title,
            desc = desc,
            category = category,
            imageUrl = imageUrl,
            sharedBy = sharedBy,
            addedToBookmark = addedToBookmark
        )
    }

    fun createVideoComponentItem(
        id: String = "123e4567-e89b-12d3-a456-426614174000",
        title: String = "A pragmatic guide to Hilt with Kotlin",
        source: String = "Youtube",
        category: String = "Dagger Hilt",
        imageUrl: String = "https://miro.medium.com/max/1400/1*9cp9m4LO4zkpgv2s30Cg9A.png",
        sharedBy: String = "Droidhub",
        addedToBookmark: Boolean = false
    ): VideoComponentItem {
        return VideoComponentItem(
            id = id,
            title = title,
            source = source,
            category = category,
            imageUrl = imageUrl,
            sharedBy = sharedBy,
            addedToBookmark = addedToBookmark
        )
    }

    fun createPodcastComponentItem(
        id: String = "123e4567-e89b-12d3-a456-426614174000",
        title: String = "A pragmatic guide to Hilt with Kotlin",
        source: String = "Youtube",
        category: String = "Podcast",
        addedToBookmark: Boolean = false
    ): PodcastComponentItem {
        return PodcastComponentItem(
            id = id, title = title, source = source, category, addedToBookmark = addedToBookmark
        )
    }
}