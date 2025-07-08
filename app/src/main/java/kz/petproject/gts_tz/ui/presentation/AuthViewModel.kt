package kz.petproject.gts_tz.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kz.petproject.gts_tz.data.local.SessionManager
import kz.petproject.gts_tz.data.network.request.LoginRequest
import kz.petproject.gts_tz.domain.use_cases.PostSiginInUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val postSiginInUseCase: PostSiginInUseCase,
    private val sessionManager: SessionManager // Changed from TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<AuthContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun onLoginChanged(login: String) {
        _state.update { it.copy(loginInput = login, error = null) }
    }

    fun onPasswordChanged(password: String) {
        _state.update { it.copy(passwordInput = password, error = null) }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val loginRequest = LoginRequest(
                login = _state.value.loginInput,
                password = _state.value.passwordInput
            )
            postSiginInUseCase(loginRequest)
                .onSuccess { (user, token) ->
                    sessionManager.saveSession(token, user) // Save full session
                    _state.update { it.copy(isLoading = false, error = null) }
                    _effect.send(AuthContract.Effect.NavigateToMain)
                }
                .onFailure { exception ->
                    _state.update { it.copy(isLoading = false, error = exception.message ?: "An unknown error occurred") }
                }
        }
    }
}