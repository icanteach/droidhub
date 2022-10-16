package co.icanteach.apps.android.droidhub.features.submission

data class SubmissionScreenUiState constructor(
    val contentUrl: String = "",
    val postTypeExpanded: Boolean = false,
    val selectedPostType: String = ""
) {

    companion object {

        fun idle(): SubmissionScreenUiState {
            return SubmissionScreenUiState(
                contentUrl = "", postTypeExpanded = false
            )
        }
    }
}