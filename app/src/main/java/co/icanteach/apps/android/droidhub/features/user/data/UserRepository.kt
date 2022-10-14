package co.icanteach.apps.android.droidhub.features.user.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(): Flow<UserEntity>
    fun insertUser(user: UserEntity)
}