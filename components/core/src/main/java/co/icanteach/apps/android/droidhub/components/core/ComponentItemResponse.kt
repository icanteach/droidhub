package co.icanteach.apps.android.droidhub.components.core

data class ComponentItemResponse(
    val id: String,
    val type: String,
    val category: String,
    val description: String,
    val title: String,
    val image: String,
    val originUrl: String,
    val sharedBy: String,
    val source: String,
)