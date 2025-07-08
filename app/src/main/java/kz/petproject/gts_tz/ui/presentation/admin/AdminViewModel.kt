package kz.petproject.gts_tz.ui.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.data.network.request.CreateUserRequest
import kz.petproject.gts_tz.domain.use_cases.CreateUserUseCase
import kz.petproject.gts_tz.domain.use_cases.GetAllUsersUseCase

class AdminViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase // <-- New dependency
) : ViewModel() {

    private val _state = MutableStateFlow(AdminContract.State())
    val state = _state.asStateFlow()

    init {
        // Fetch real users when the ViewModel is created
        loadUsers()
    }

    fun onRefresh() {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getAllUsersUseCase()
                .onSuccess { users ->
                    _state.update { it.copy(users = users, isLoading = false) }
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            error = exception.message ?: "Failed to load users",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onNameChange(name: String) = _state.update { it.copy(newUserName = name, error = null, successMessage = null) }
    fun onLoginChange(login: String) = _state.update { it.copy(newUserLogin = login, error = null, successMessage = null) }
    fun onPasswordChange(password: String) = _state.update { it.copy(newUserPassword = password, error = null, successMessage = null) }
    fun onRoleChange(role: String) = _state.update { it.copy(selectedRole = role, error = null, successMessage = null) }

    fun onCreateUser() {
        viewModelScope.launch {
            // To prevent the full-screen loader from showing, we keep the user list.
            // The button's internal spinner will indicate the loading state.
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
                            users = it.users + newUser,
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