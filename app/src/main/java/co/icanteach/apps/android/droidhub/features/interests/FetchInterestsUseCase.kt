package co.icanteach.apps.android.droidhub.features.interests

import co.icanteach.apps.android.droidhub.features.core.data.IoDispatcher
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val MAIN_INTERESTS_PATH = "droidhub-interests/droidhub-main-interests"
private const val INTEREST_ID = "id"
private const val INTEREST_DISPLAY_NAME = "display_name"
private const val USERS_COLLECTION = "droidhub-users"

class FetchInterestsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchInterests(
    ): Flow<List<InterestItem>> {
        val mainInterestDocumentRef = firestore.document(MAIN_INTERESTS_PATH)
        val mainInterestDocument = mainInterestDocumentRef.get().await()

        return userRepository.getInterests().mapLatest { interests ->
            mainInterestDocument.data?.mapNotNull { result ->
                val interestResult = result.value as HashMap<String, String>
                InterestItem(id = interestResult[INTEREST_ID].toString(),
                    displayName = interestResult[INTEREST_DISPLAY_NAME].toString(),
                    isSelected = interests.any { it.id == interestResult[INTEREST_ID].toString() })
            } ?: listOf()
        }


    }

}


data class InterestItem(
    val id: String, val displayName: String, val isSelected: Boolean
)