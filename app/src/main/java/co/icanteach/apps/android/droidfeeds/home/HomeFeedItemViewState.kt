package co.icanteach.apps.android.droidfeeds.home

import co.icanteach.apps.android.droidfeeds.news.FeedItem

class HomeFeedItemViewState(val newsItem: FeedItem) {

    fun shouldShowCategoryInfo(): Boolean {
        return newsItem.category.isNullOrEmpty().not()
    }
}