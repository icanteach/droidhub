package co.icanteach.apps.android.droidhub.components.core

data class PodcastComponentItem(
    override val id: String,
    val title: String,
    val source: String,
    val category: String,
    override val addedToBookmark: Boolean
) : ComponentItem {
    override fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id,
            "category" to category,
            "title" to title,
            "source" to source,
        )
    }
}