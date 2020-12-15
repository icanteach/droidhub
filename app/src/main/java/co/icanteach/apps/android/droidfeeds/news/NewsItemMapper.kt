package co.icanteach.apps.android.droidfeeds.news

import co.icanteach.apps.android.droidfeeds.data.repository.model.NewsResponse
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import javax.inject.Inject

class NewsItemMapper @Inject constructor() {

    fun mapFrom(response: List<NewsResponse>): HomeFeedListing {
        val list = response.map {
            NewsItem(
                originUrl = it.originUrl,
                image = it.image,
                title = it.title,
                description = it.description
            )
        }
        return HomeFeedListing(list)
    }
}