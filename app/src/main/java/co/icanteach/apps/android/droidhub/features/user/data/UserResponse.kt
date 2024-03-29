package co.icanteach.apps.android.droidhub.features.user.data

data class UserResponse(
    val name: String = "",
    val email: String = "",
    val id: String = "",
    val interests: List<String> = mutableListOf()
)