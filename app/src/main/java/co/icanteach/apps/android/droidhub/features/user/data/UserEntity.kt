package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    val name: String,
    val email: String,
    @PrimaryKey(autoGenerate = false) val id: String,
)