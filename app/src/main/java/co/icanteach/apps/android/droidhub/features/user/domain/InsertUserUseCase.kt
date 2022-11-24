package co.icanteach.apps.android.droidhub.features.user.domain

import co.icanteach.apps.android.droidhub.features.user.data.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val USERS_COLLECTION = "droidhub-users"
private const val USERS_BOOKMARKS = "user_bookmarks"

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
                val bookmarks = user?.get("bookmarks")

                if (interests is List<*>) {
                    insertAllInterests(interests)
                }

                if (bookmarks is List<*>) {
                    insertAllBookmarks(bookmarks)
                }

            }
            val bookmarkItem = hashMapOf(
                "bookmark_items" to emptyList<Any>(),
            )

            firestore.collection(USERS_BOOKMARKS).document(id).set(bookmarkItem).await()
        } catch (e: Exception) {
            // todo handle exception.
        }
    }

    private suspend fun insertAllInterests(interests: List<*>) {
        val interestEntities = interests.map { item ->
            item as String
            InterestEntity(item)
        }
        repository.insertAllInterests(interestEntities)
    }

    private suspend fun insertAllBookmarks(interests: List<*>) {
        val bookmarkEntities = interests.map { item ->
            item as String
            BookmarkEntity(item)
        }
        repository.insertAllBookmarks(bookmarkEntities)
    }
}