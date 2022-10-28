package co.icanteach.apps.android.droidhub.features.interests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.user.domain.UpdateUserInterestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InterestsViewModel @Inject constructor(
    private val updateUserInterestsUseCase: UpdateUserInterestsUseCase,
    private val fetchInterestsUseCase: FetchInterestsUseCase
) : ViewModel() {


    var interestScreenState by mutableStateOf(
        InterestsScreenUiState()
    )
        private set

    init {
        fetchInterests()
    }

    private fun fetchInterests() {
        viewModelScope.launch {
            val result = fetchInterestsUseCase.fetchInterests()
            interestScreenState = interestScreenState.copy(items = result)
        }
    }

    fun onInterestSelection(item: InterestItem) {
        viewModelScope.launch {
            if (item.isSelected) {
                updateUserInterestsUseCase.unSelectInterest(item.id)
            } else {
                updateUserInterestsUseCase.selectInterest(item.id)
            }
        }
    }

}