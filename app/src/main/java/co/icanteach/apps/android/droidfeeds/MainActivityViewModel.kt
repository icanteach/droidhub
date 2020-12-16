package co.icanteach.apps.android.droidfeeds

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.apps.android.droidfeeds.auth.AuthenticationUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    init {
        auth()
    }

    fun auth() {
        viewModelScope.launch {
            authenticationUseCase.authenticate().collect {}
        }
    }
}