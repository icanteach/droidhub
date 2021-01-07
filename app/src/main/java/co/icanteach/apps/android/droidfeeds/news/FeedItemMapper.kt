package co.icanteach.apps.android.droidfeeds.news

import co.icanteach.apps.android.droidfeeds.data.repository.model.NewsResponse
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import javax.inject.Inject

class FeedItemMapper @Inject constructor() {

    fun mapFrom(response: List<NewsResponse>): HomeFeedListing {
        val list = response.map {
            FeedItem(
                originUrl = it.originUrl,
                image = it.image,
                title = it.title,
                description = it.description,
                category = it.category
            )
        }
        return HomeFeedListing(list)
    }
}