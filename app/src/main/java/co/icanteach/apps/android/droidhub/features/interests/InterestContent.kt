package co.icanteach.apps.android.droidhub.features.interests

data class InterestContent constructor(
    val id: String, val displayName: String, val followedBy: Boolean = false
) {}