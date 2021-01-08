package co.icanteach.apps.android.droidhub

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidhub.auth.AuthenticationUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    fun auth() {
        viewModelScope.launch {
            authenticationUseCase.authenticate().collect {}
        }
    }
}