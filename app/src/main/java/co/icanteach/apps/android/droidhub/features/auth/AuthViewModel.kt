package co.icanteach.apps.android.droidhub.features.auth

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.features.user.domain.InsertUserUseCase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun authUser(credential: AuthCredential) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = FirebaseAuth.getInstance().signInWithCredential(credential).await()
            result.user?.let { user ->
                insertUserUseCase.insert(user.uid, user.displayName, user.email)
                _eventFlow.emit(UiEvent.ClosePage)
            }
        }
    }

    sealed class UiEvent {
        data class ShowError(@StringRes val message: Int) : UiEvent()
        object ClosePage : UiEvent()
    }
}