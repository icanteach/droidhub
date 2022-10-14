package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "droidhub_user_db"
    }
}