package co.icanteach.apps.android.droidhub.features.interests

class FakeInterestProvider {

    val interests by lazy {
        listOf(
            InterestItem(
                id = "id_id_id", displayName = "Android", isSelected = false
            ), InterestItem(
                id = "id_id_id", displayName = "Jetpack Compose", isSelected = true
            ), InterestItem(
                id = "id_id_id", displayName = "Android Arch.", isSelected = true
            ), InterestItem(
                id = "id_id_id", displayName = "Dagger Hilt", isSelected = false
            ), InterestItem(
                id = "id_id_id", displayName = "Android", isSelected = true
            )
        )
    }
}