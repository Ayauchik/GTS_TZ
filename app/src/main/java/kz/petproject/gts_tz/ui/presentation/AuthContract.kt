package kz.petproject.gts_tz.ui.presentation

// A contract file helps to define the interaction between UI and ViewModel
object AuthContract {
    data class State(
        val loginInput: String = "",
        val passwordInput: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class Effect {
        data object NavigateToMain : Effect()
    }
}