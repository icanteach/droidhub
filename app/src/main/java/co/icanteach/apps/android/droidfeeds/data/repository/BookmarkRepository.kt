package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.Resource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val BOOKMARK_COLLECTION = "bookmarks"

class BookmarkRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val bookmarkCollections =
        firestore.collection(BOOKMARK_COLLECTION)

    fun createBookmarkDocument(documentId: String) = flow {

        val postRef = bookmarkCollections
            .document(documentId)
            .set(true)
            .await()

        emit(Resource.Success(postRef))

    }.catch {
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
}