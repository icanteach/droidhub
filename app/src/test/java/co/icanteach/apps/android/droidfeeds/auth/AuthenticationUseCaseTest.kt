package co.icanteach.apps.android.droidfeeds.auth

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.AuthenticationDataRepository
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidfeeds.data.repository.model.UserResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AuthenticationUseCaseTest {


    @MockK
    lateinit var authenticationRepository: AuthenticationDataRepository

    @MockK
    lateinit var bookmarkRepository: BookmarkRepository

    private lateinit var authenticationUseCase: AuthenticationUseCase

    @Before
    fun setUp() {

        MockKAnnotations.init(this)


        authenticationUseCase = AuthenticationUseCase(
            authenticationRepository, bookmarkRepository
        )
    }

    @Test
    fun `given success authenticate result from authenticationRepository, then should call createBookmarkDocument`() {

        runBlocking {
            // Given
            val userId = "999"

            val flowResult = flow {
                emit(Resource.Success(UserResponse(userId)))
            }

            val flowResult2 = flow {
                emit(Resource.Success(true))
            }

            every {
                authenticationRepository.authenticate()
            } returns flowResult
            every {
                bookmarkRepository.createBookmarkDocument(userId)
            } returns flowResult2

            // When
            authenticationUseCase.authenticate().collect { }

            // Then
            verify(exactly = 1) {
                bookmarkRepository.createBookmarkDocument(userId)
            }
        }
    }
}