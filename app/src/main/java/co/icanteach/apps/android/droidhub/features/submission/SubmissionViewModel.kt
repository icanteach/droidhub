package co.icanteach.apps.android.droidhub.features.submission

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.submission.domain.SendSubmissionFormUseCase
import co.icanteach.apps.android.droidhub.features.submission.domain.SubmissionError
import co.icanteach.apps.android.droidhub.features.submission.domain.SubmissionSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmissionViewModel @Inject constructor(
    private val submissionUseCase: SendSubmissionFormUseCase
) : ViewModel() {

    var state by mutableStateOf(SubmissionScreenUiState.idle())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SubmissionScreenEvent) {
        when (event) {
            is SubmissionScreenEvent.ContentUrlChanged -> {
                state = state.copy(contentUrl = event.url)
            }
            SubmissionScreenEvent.SubmitForm -> {
                onSubmitForm()
            }
            is SubmissionScreenEvent.PostTypeExpanded -> {
                state = state.copy(postTypeExpanded = event.expanded)
            }
            is SubmissionScreenEvent.PostTypeChanged -> {
                state = state.copy(selectedPostType = event.type)
            }
        }
    }

    private fun onSubmitForm() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = submissionUseCase.send(
                url = state.contentUrl, postType = state.selectedPostType
            )
            when (result) {
                SubmissionError -> {
                    _eventFlow.emit(UiEvent.ShowError("Error"))

                }
                SubmissionSuccess -> {
                    _eventFlow.emit(UiEvent.ClosePage)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowError(val message: String) : UiEvent()
        object ClosePage : UiEvent()
    }
}