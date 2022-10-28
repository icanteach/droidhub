package co.icanteach.apps.android.droidhub.features.user.domain

import co.icanteach.apps.android.droidhub.features.user.data.UserEntity
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import co.icanteach.apps.android.droidhub.features.user.data.UserRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_COLLECTION = "droidhub-users"

class InsertUserUseCase @Inject constructor(
    private val firestore: FirebaseFirestore, private val repository: UserRepository
) {

    suspend fun insert(id: String, name: String?, email: String?) {
        val userEntity = UserEntity(id = id, name = name.orEmpty(), email = email.orEmpty())
        val userRequest = UserRequest(id = id, name = name.orEmpty(), email = email.orEmpty())
        try {
            firestore.collection(USERS_COLLECTION).document(id).set(userRequest).await()
            repository.insertUser(userEntity)
        } catch (e: Exception) {
            // todo handle exception.
        }
    }
}