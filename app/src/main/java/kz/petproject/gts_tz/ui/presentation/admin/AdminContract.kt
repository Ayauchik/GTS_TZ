package kz.petproject.gts_tz.ui.presentation.admin

import kz.petproject.gts_tz.data.User

object AdminContract {
    data class State(
        val users: List<User> = emptyList(),
        val newUserName: String = "",
        val newUserLogin: String = "",
        val newUserPassword: String = "",
        val selectedRole: String? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val successMessage: String? = null
    )
}