package co.icanteach.apps.android.droidfeeds.news

import co.icanteach.apps.android.droidfeeds.data.source.remote.model.NewsResponse

class NewsItemMapper {
    fun mapFrom(response: List<NewsResponse>): List<NewsItem> {
        return response.map { it
            NewsItem(
                originUrl = it.originUrl,
                image = it.image,
                title = it.title,
                description = it.description
            )
        }
    }
}