package co.icanteach.apps.android.droidhub.features.user.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app, UserDatabase::class.java, UserDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideInterestsDatabase(app: Application): InterestsDatabase {
        return Room.databaseBuilder(
            app, InterestsDatabase::class.java, InterestsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDb: UserDatabase, interestDb: InterestsDatabase): UserRepository {
        return UserRepositoryImpl(userDb.userDao, interestDb.interestsDao)
    }
}