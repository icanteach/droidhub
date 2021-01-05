package co.icanteach.apps.android.droidfeeds.auth

import co.icanteach.apps.android.droidfeeds.core.Resource
import co.icanteach.apps.android.droidfeeds.data.repository.AuthenticationDataRepository
import co.icanteach.apps.android.droidfeeds.data.repository.BookmarkRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 *  The AuthenticationUseCase class is responsible for all authentication actions throughout the application.
 */

class AuthenticationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationDataRepository,
    private val bookmarkRepository: BookmarkRepository
) {

    /**
     * authenticate user via AuthenticationDataRepository.
     *
     * If the authentication process is successful, create an empty bookmark collection for the user.
     */
    fun authenticate(): Flow<Resource<Any>> {
        return authenticationRepository
            .authenticate()
            .flatMapConcat {
                return@flatMapConcat if (it is Resource.Success) {
                    bookmarkRepository.createBookmarkDocument(it.data.userId)
                } else {
                    flow { emit(it) }
                }
            }
    }

    fun getUserId(): String {
        return authenticationRepository.getUserId()
    }
}