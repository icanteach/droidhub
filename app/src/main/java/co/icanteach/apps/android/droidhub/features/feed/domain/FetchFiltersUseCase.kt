package co.icanteach.apps.android.droidhub.features.feed.domain

import co.icanteach.apps.android.droidhub.features.interests.FetchInterestsUseCase
import javax.inject.Inject

class FetchFiltersUseCase @Inject constructor(
    private val fetchInterestsUseCase: FetchInterestsUseCase
) {

    suspend fun fetchFilters(): List<FilterItem> {
        return fetchInterestsUseCase.fetchInterests().map { response ->
            FilterItem(id = response.id, displayName = response.displayName)
        }.sortedBy { it.displayName }
    }
}

data class FilterItem(
    val id: String, val displayName: String, val isSelected: Boolean = false
)