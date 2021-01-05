package co.icanteach.apps.android.droidfeeds.bookmark.domain

import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCase
import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidfeeds.data.repository.model.NewsResponse
import co.icanteach.apps.android.droidfeeds.home.domain.HomeFeedListing
import co.icanteach.apps.android.droidfeeds.news.NewsItemMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class BookmarkActionsUseCaseTest {

    @MockK
    lateinit var authenticationUseCase: AuthenticationUseCase

    @MockK
    lateinit var bookmarkRepository: BookmarkRepository

    @MockK
    lateinit var mapper: NewsItemMapper

    lateinit var bookmarkActionsUseCase: BookmarkActionsUseCase

    @Before
    fun setUp() {

        MockKAnnotations.init(this)

        bookmarkActionsUseCase = BookmarkActionsUseCase(
            authenticationUseCase, bookmarkRepository, mapper
        )
    }

    @Test
    fun `given addBookmark action, then verify bookmarkRepository addBookmark with correct parameters`() {

        // Given
        val originUrl = "originUrl"
        val image = "image"
        val title = "title"
        val description = "description"

        val userId = "userId"

        val requestMap = mapOf(
            "originUrl" to originUrl,
            "image" to image,
            "title" to title,
            "description" to description,
        )

        runBlocking {
            val flowResult = flow {
                emit(Resource.Success(true))
            }

            every {
                authenticationUseCase.getUserId()
            } returns userId

            every {
                bookmarkRepository
                    .addBookmark(bookmarkItem = requestMap, documentId = userId)
            } returns flowResult

            // When
            bookmarkActionsUseCase
                .addBookmark(
                    originUrl, image, title, description
                ).collect { }

            // Then
            verify(exactly = 1) {
                authenticationUseCase.getUserId()
            }

            verify(exactly = 1) {
                bookmarkRepository
                    .addBookmark(bookmarkItem = requestMap, documentId = userId)
            }
        }
    }

    @Test
    fun `given removeBookmark action, then verify bookmarkRepository removeBookmark with correct parameters`() {

        // Given
        val originUrl = "originUrl"
        val image = "image"
        val title = "title"
        val description = "description"

        val userId = "userId"

        val requestMap = mapOf(
            "originUrl" to originUrl,
            "image" to image,
            "title" to title,
            "description" to description,
        )

        runBlocking {

            val bookmarkResponse = listOf(NewsResponse())
            val flowResult = flow {
                emit(Resource.Success((bookmarkResponse)))
            }

            every {
                mapper.mapFrom(bookmarkResponse)
            } returns HomeFeedListing(emptyList())

            every {
                authenticationUseCase.getUserId()
            } returns userId

            every {
                bookmarkRepository
                    .removeBookmark(bookmarkItem = requestMap, documentId = userId)
            } returns flowResult

            // When
            bookmarkActionsUseCase
                .removeBookmark(
                    originUrl, title, description, image
                ).collect { }

            // Then
            verify(exactly = 1) {
                authenticationUseCase.getUserId()
            }

            verify (exactly = 1){
                mapper.mapFrom(bookmarkResponse)
            }

            verify(exactly = 1) {
                bookmarkRepository
                    .removeBookmark(bookmarkItem = requestMap, documentId = userId)
            }
        }
    }
}