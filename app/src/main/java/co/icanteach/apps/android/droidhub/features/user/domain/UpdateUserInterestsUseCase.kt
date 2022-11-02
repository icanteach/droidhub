package co.icanteach.apps.android.droidhub.features.user.domain

import co.icanteach.apps.android.droidhub.features.user.data.InterestEntity
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_COLLECTION = "droidhub-users"
private const val FIELD_INTERESTS = "interests"

class UpdateUserInterestsUseCase @Inject constructor(
    private val firestore: FirebaseFirestore, private val userRepository: UserRepository
) {

    suspend fun selectInterest(interestId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(FIELD_INTERESTS, FieldValue.arrayUnion(interestId)).await()

        val entity = InterestEntity(id = interestId)
        userRepository.insertInterest(entity)
    }

    suspend fun unSelectInterest(interestId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(FIELD_INTERESTS, FieldValue.arrayRemove(interestId)).await()

        val entity = InterestEntity(id = interestId)
        userRepository.deleteInterest(entity)
    }
}