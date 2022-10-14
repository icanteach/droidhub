package co.icanteach.apps.android.droidhub.features.user.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {
    override fun getUser(): Flow<UserEntity> {
        return dao.getUser()
    }

    override fun insertUser(user: UserEntity) {
        dao.insertUser(user)
    }
}