package co.icanteach.apps.android.droidhub.features.user.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "interests")
data class InterestEntity(
    @PrimaryKey(autoGenerate = false) val id: String
)