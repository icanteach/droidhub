package co.icanteach.apps.android.droidhub.features.user.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_COLLECTION = "droidhub-users"
private const val FIELD_INTERESTS = "interests"

class UpdateUserInterestsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    suspend fun selectInterest(interestId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(FIELD_INTERESTS, FieldValue.arrayUnion(interestId)).await()
    }

    suspend fun unSelectInterest(interestId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(FIELD_INTERESTS, FieldValue.arrayRemove(interestId)).await()
    }
}