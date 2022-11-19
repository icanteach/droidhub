package co.icanteach.apps.android.droidhub.components.core

data class ArticleComponentItem(
    override val id: String,
    val category: String,
    val title: String,
    val desc: String,
    val imageUrl: String,
    val sharedBy: String,
    override val addedToBookmark: Boolean
) : ComponentItem {

    override fun toMap(): Map<String, String> {
        return mapOf(
            "id" to id,
            "sharedBy" to sharedBy,
            "category" to category,
            "title" to title,
            "desc" to desc,
            "imageUrl" to imageUrl
        )
    }
}