package co.icanteach.apps.android.droidhub.features.interests

import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class InitInterestsUseCase @Inject constructor(
    private val fetchInterestsUseCase: FetchInterestsUseCase,
    private val userRepository: UserRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchInterests(
    ): Flow<List<InterestItem>> {
        return userRepository.getInterests().mapLatest { interests ->
            fetchInterestsUseCase.fetchInterests().map { item ->
                InterestItem(id = item.id,
                    displayName = item.displayName,
                    isSelected = interests.any { it.id == item.id })
            }
        }
    }
}

data class InterestItem(
    val id: String, val displayName: String, val isSelected: Boolean
)