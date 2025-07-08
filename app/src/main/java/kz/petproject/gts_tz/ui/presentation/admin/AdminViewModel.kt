package kz.petproject.gts_tz.ui.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.data.network.request.CreateUserRequest
import kz.petproject.gts_tz.domain.use_cases.CreateUserUseCase

class AdminViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AdminContract.State())
    val state = _state.asStateFlow()

    init {
        //TODO: For now, load dummy users. Later, this could be a network call.
        _state.update { it.copy(users = DummyData.users) }
    }

    fun onNameChange(name: String) = _state.update { it.copy(newUserName = name, error = null, successMessage = null) }
    fun onLoginChange(login: String) = _state.update { it.copy(newUserLogin = login, error = null, successMessage = null) }
    fun onPasswordChange(password: String) = _state.update { it.copy(newUserPassword = password, error = null, successMessage = null) }
    fun onRoleChange(role: String) = _state.update { it.copy(selectedRole = role, error = null, successMessage = null) }

    fun onCreateUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, successMessage = null) }

            val request = CreateUserRequest(
                name = _state.value.newUserName.trim(),
                login = _state.value.newUserLogin.trim(),
                password = _state.value.newUserPassword,
                role = _state.value.selectedRole!!
            )

            createUserUseCase(request)
                .onSuccess { newUser ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            successMessage = "User '${newUser.name}' created successfully!",
                            users = it.users + newUser, // Add new user to the list
                            // Reset form fields
                            newUserName = "",
                            newUserLogin = "",
                            newUserPassword = "",
                            selectedRole = null
                        )
                    }
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "An unknown error occurred"
                        )
                    }
                }
        }
    }
}