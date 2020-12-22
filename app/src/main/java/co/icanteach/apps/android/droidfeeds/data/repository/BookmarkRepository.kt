package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.NoContentListingException
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.model.HomeFeedDocument
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.HashMap
import javax.inject.Inject

private const val BOOKMARK_COLLECTION = "bookmarks"

class BookmarkRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val bookmarkCollections =
        firestore.collection(BOOKMARK_COLLECTION)

    fun createBookmarkDocument(documentId: String) = flow {

        emit(Resource.Loading)

        val confirmData = hashMapOf(
            "exists" to true
        )

        val postRef = bookmarkCollections
            .document(documentId)
            .set(confirmData)
            .await()

        emit(Resource.Success(true))

    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)

    fun addBookmark(
        bookmarkItem: Map<String, Any>,
        documentId: String,
    ) = flow {

        // Emit loading state
        emit(Resource.Loading)

        val postRef = bookmarkCollections
            .document(documentId).update("contents", FieldValue.arrayUnion(bookmarkItem))
            .await()


        // Emit success state with post reference
        emit(Resource.Success(postRef))

    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)

    fun fetchUserBookmarks(documentId: String) = flow {

        emit(Resource.Loading)

        val snapshot = bookmarkCollections.document(documentId).get().await()
        val homeFeed = snapshot.toObject(HomeFeedDocument::class.java)

        homeFeed?.let {
            if (homeFeed.contents.isEmpty()) {
                emit(
                    Resource.Error(
                        NoContentListingException()
                    )
                )
            } else {
                emit(Resource.Success(homeFeed.contents))
            }
        } ?: emit(
            Resource.Error(
                NoContentListingException()
            )
        )
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(Dispatchers.IO)

    fun removeBookmark(
        bookmarkItem: Map<String, Any>,
        documentId: String,
    ) = flow {

        // Emit loading state
        emit(Resource.Loading)

        val postRef = bookmarkCollections
            .document(documentId).update("contents", FieldValue.arrayRemove(bookmarkItem))
            .await()


        // Emit success state with post reference
        emit(Resource.Success(postRef))

    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)

}