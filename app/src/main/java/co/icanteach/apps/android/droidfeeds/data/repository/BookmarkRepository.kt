package co.icanteach.apps.android.droidfeeds.data.repository

import co.icanteach.apps.android.droidfeeds.core.NoContentListingException
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.model.FeedDocumentResponse
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val BOOKMARK_COLLECTION = "bookmarks"
private const val BOOKMARK_PARENT_FIELD = "contents"
private const val BOOKMARK_CREATE_CONFIRM_DATA = "exists"

class BookmarkRepository @Inject constructor(
    firestore: FirebaseFirestore
) {

    private val bookmarkCollections =
        firestore.collection(BOOKMARK_COLLECTION)

    fun createBookmarkDocument(documentId: String) = flow {

        emit(Resource.Loading)

        val confirmData = hashMapOf(
            BOOKMARK_CREATE_CONFIRM_DATA to true
        )

        bookmarkCollections
            .document(documentId)
            .set(confirmData)
            .await()

        emit(Resource.Success(true))

    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)

    fun fetchUserBookmarks(documentId: String) = flow {

        emit(Resource.Loading)

        val snapshot = bookmarkCollections.document(documentId).get().await()
        val bookmarksResponse = snapshot.toObject(FeedDocumentResponse::class.java)

        bookmarksResponse?.let {
            if (bookmarksResponse.contents.isEmpty()) {
                emit(
                    Resource.Error(
                        NoContentListingException()
                    )
                )
            } else {
                emit(Resource.Success(bookmarksResponse.contents))
            }
        } ?: emit(
            Resource.Error(
                NoContentListingException()
            )
        )
    }.catch { exception ->
        emit(Resource.Error(exception))
    }.flowOn(Dispatchers.IO)

    fun addBookmark(
        bookmarkItem: Map<String, Any>,
        documentId: String,
    ) = flow {

        // Emit loading state
        emit(Resource.Loading)

        bookmarkCollections
            .document(documentId).update(BOOKMARK_PARENT_FIELD, FieldValue.arrayUnion(bookmarkItem))
            .await()

        // Emit success state with post reference
        emit(Resource.Success(true))

    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)


    @ExperimentalCoroutinesApi
    fun removeBookmark(
        bookmarkItem: Map<String, Any>,
        documentId: String,
    ) = callbackFlow {

        // Emit loading state
        offer(Resource.Loading)

        val bookmarkDocument = bookmarkCollections.document(documentId)

        bookmarkDocument
            .update(BOOKMARK_PARENT_FIELD, FieldValue.arrayRemove(bookmarkItem))
            .await()


        // Register listener
        val listener = bookmarkDocument.addSnapshotListener { snapshot, exception ->

            val bookmarksResponse = snapshot?.toObject(FeedDocumentResponse::class.java)

            bookmarksResponse?.let {
                if (bookmarksResponse.contents.isEmpty()) {
                    offer(
                        Resource.Error(
                            NoContentListingException()
                        )
                    )
                } else {
                    offer(Resource.Success(bookmarksResponse.contents))
                }
            } ?: offer(
                Resource.Error(
                    NoContentListingException()
                )
            )

            // If exception occurs, cancel this scope with exception message.
            exception?.let {
                offer(Resource.Error(exception))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            // This block is executed when producer channel is cancelled
            // This function resumes with a cancellation exception.
            // Dispose listener
            listener.remove()
            cancel()
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(Dispatchers.IO)

}