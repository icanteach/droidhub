package co.icanteach.apps.android.droidhub.features.user

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun to(array: Array<String>): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun from(value: String): List<String> {
        return value.split(",")
    }
}