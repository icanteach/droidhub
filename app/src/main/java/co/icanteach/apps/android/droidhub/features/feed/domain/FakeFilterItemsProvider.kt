package co.icanteach.apps.android.droidhub.features.feed.domain


class FakeFilterItemsProvider {

    val filters by lazy {
        mutableListOf(
            FilterItem(
                id = "id_id_id", displayName = "Android", isSelected = true
            ), FilterItem(
                id = "id_id_id", displayName = "Jetpack Compose", isSelected = false
            ), FilterItem(
                id = "id_id_id", displayName = "Android Arch.", isSelected = false
            ), FilterItem(
                id = "id_id_id", displayName = "Dagger Hilt", isSelected = false
            ), FilterItem(
                id = "id_id_id", displayName = "Android", isSelected = false
            )
        )
    }
}