package co.icanteach.apps.android.droidhub.features.user.domain

import co.icanteach.apps.android.droidhub.features.user.data.InterestEntity
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

        val userRequest = UserRequest(id = id, name = name.orEmpty(), email = email.orEmpty())
        try {

            val userDocument = firestore.collection(USERS_COLLECTION).document(id)
            val isUserDocumentExists = userDocument.get().await().exists()

            if (isUserDocumentExists.not()) {
                userDocument.set(userRequest).await()
                repository.insertUser(
                    UserEntity(
                        id = id, name = name.orEmpty(), email = email.orEmpty()
                    )
                )
            } else {
                val user = userDocument.get().await().data
                repository.insertUser(
                    UserEntity(
                        id = user?.get("id").toString(),
                        name = user?.get("name").toString(),
                        email = user?.get("email").toString(),
                    )
                )

                val interests = user?.get("interests")
                if (interests is List<*>) {
                    val interestEntities = interests.map { item ->
                        item as String
                        InterestEntity(item)
                    }
                    repository.insertAllInterests(interestEntities)
                }
            }
        } catch (e: Exception) {
            // todo handle exception.
        }
    }
}