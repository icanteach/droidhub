package co.icanteach.apps.android.droidhub.components.core

interface ComponentItem {
    val id: String
    val addedToBookmark: Boolean
    fun toMap(): Map<String, String>
}