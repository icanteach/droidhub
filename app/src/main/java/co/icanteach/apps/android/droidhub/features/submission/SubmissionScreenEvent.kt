package co.icanteach.apps.android.droidhub.features.submission

sealed class SubmissionScreenEvent {
    data class ContentUrlChanged(val url: String) : SubmissionScreenEvent()
    data class PostTypeExpanded(val expanded: Boolean) : SubmissionScreenEvent()
    data class PostTypeChanged(val type: String) : SubmissionScreenEvent()
    object SubmitForm : SubmissionScreenEvent()
}