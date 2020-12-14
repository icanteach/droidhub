package co.icanteach.apps.android.droidfeeds.home.domain

import co.icanteach.apps.android.droidfeeds.data.repository.DroidFeedsRepository
import co.icanteach.apps.android.droidfeeds.news.NewsItemMapper

class FetchHomeFeedUseCase(
    val repository: DroidFeedsRepository,
    val mapper: NewsItemMapper
) {
}