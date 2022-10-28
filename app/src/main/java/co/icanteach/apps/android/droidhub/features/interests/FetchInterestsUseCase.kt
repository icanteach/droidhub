package co.icanteach.apps.android.droidhub.features.interests

import co.icanteach.apps.android.droidhub.features.core.data.IoDispatcher
import co.icanteach.apps.android.droidhub.features.user.data.UserResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val MAIN_INTERESTS_PATH = "droidhub-interests/droidhub-main-interests"
private const val INTEREST_ID = "id"
private const val INTEREST_DISPLAY_NAME = "display_name"
private const val USERS_COLLECTION = "droidhub-users"

class FetchInterestsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    @Suppress("UNCHECKED_CAST")
    suspend fun fetchInterests(
    ): List<InterestItem> {
        val mainInterestDocumentRef = firestore.document(MAIN_INTERESTS_PATH)
        val mainInterestDocument = mainInterestDocumentRef.get().await()

        return mainInterestDocument.data?.mapNotNull { result ->
            val userInterests = fetchUserInterests()
            val interestResult = result.value as HashMap<String, String>
            InterestItem(
                id = interestResult[INTEREST_ID].toString(),
                displayName = interestResult[INTEREST_DISPLAY_NAME].toString(),
                isSelected = userInterests.contains(interestResult[INTEREST_ID].toString())
            )
        }?.sortedByDescending { item ->
            item.isSelected
        } ?: listOf()
    }

    private suspend fun fetchUserInterests(): List<String> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val document = firestore.collection(USERS_COLLECTION).document(userId).get().await()
        val user = document.toObject(UserResponse::class.java)
        return user?.interests ?: emptyList()
    }
}

data class InterestItem constructor(
    val id: String, val displayName: String, val isSelected: Boolean
)