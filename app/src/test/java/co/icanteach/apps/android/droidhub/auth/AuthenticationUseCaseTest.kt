package co.icanteach.apps.android.droidhub.auth


import co.icanteach.apps.android.droidhub.core.Resource
import co.icanteach.apps.android.droidhub.data.repository.AuthenticationDataRepository
import co.icanteach.apps.android.droidhub.data.repository.BookmarkRepository
import co.icanteach.apps.android.droidhub.data.repository.model.UserResponse
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
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

            val authenticateCallResult = flow {
                emit(Resource.Success(UserResponse(userId)))
            }

            val documentCreationResult = flow {
                emit(Resource.Success(true))
            }

            every {
                authenticationRepository.authenticate()
            } returns authenticateCallResult
            every {
                bookmarkRepository.createBookmarkDocument(userId)
            } returns documentCreationResult

            // When
            authenticationUseCase.authenticate().collect { }

            // Then
            verify(exactly = 1) {
                bookmarkRepository.createBookmarkDocument(userId)
            }
        }
    }

    @Test
    fun `given error authenticate result from authenticationRepository, then should not call createBookmarkDocument`() {

        runBlocking {
            // Given
            val userId = "999"

            val authenticateCallResult = flow {
                emit(Resource.Error(Exception()))
            }

            val documentCreationResult = flow {
                emit(Resource.Success(true))
            }

            every {
                authenticationRepository.authenticate()
            } returns authenticateCallResult
            every {
                bookmarkRepository.createBookmarkDocument(userId)
            } returns documentCreationResult

            // When
            authenticationUseCase.authenticate().collect { }

            // Then
            verify(exactly = 0) {
                bookmarkRepository.createBookmarkDocument(userId)
            }
        }
    }

    @After
    fun clear() {
        clearAllMocks()
    }
}