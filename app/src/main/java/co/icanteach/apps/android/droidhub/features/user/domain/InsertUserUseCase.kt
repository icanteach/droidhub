package co.icanteach.apps.android.droidhub.features.user.domain

import co.icanteach.apps.android.droidhub.features.user.data.UserEntity
import co.icanteach.apps.android.droidhub.features.user.data.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    fun insert(id: String, name: String?, email: String?) {
        // TODO if user name and email are null, we will check them and add them to db accordingly
        val userEntity = UserEntity(id = id, name = name.orEmpty(), email = email.orEmpty())
        repository.insertUser(userEntity)
    }
}