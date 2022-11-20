package co.icanteach.apps.android.droidhub.features.interests

import co.icanteach.apps.android.droidhub.components.core.ID_FIELD
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val MAIN_INTERESTS_PATH = "droidhub-interests/droidhub-main-interests"
private const val INTEREST_DISPLAY_NAME = "display_name"

class FetchInterestsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    suspend fun fetchInterests(
    ): List<InterestItemResponse> {
        val mainInterestDocumentRef = firestore.document(MAIN_INTERESTS_PATH)
        val mainInterestDocument = mainInterestDocumentRef.get().await()
        return mainInterestDocument.data?.mapNotNull { result ->
            val interestResult = result.value as HashMap<*, *>
            InterestItemResponse(
                id = interestResult[ID_FIELD].toString(),
                displayName = interestResult[INTEREST_DISPLAY_NAME].toString(),
            )
        } ?: listOf()
    }

}

data class InterestItemResponse(
    val id: String, val displayName: String
)