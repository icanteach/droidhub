package co.icanteach.apps.android.droidhub.home

import co.icanteach.apps.android.droidhub.news.FeedItem

class HomeFeedItemViewState(val newsItem: FeedItem) {

    fun shouldShowCategoryInfo(): Boolean {
        return newsItem.category.isNullOrEmpty().not()
    }
}