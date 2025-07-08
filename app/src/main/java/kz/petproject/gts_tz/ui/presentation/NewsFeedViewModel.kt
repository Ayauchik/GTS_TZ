package kz.petproject.gts_tz.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.data.local.SessionManager
import kz.petproject.gts_tz.domain.use_cases.GetPublishedArticledUseCase

class NewsFeedViewModel(
    private val sessionManager: SessionManager,
    private val getPublishedArticlesUseCase: GetPublishedArticledUseCase // <-- New dependency
) : ViewModel() {

    private val _state = MutableStateFlow(NewsFeedContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<NewsFeedContract.Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        // Automatically load content when the ViewModel is created
        loadContent()
    }

    /**
     * Public function to allow the UI to trigger a refresh.
     */
    fun onRefresh() {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            // Set loading state to true before the network call
            _state.update { it.copy(isLoading = true, error = null) }
            getPublishedArticlesUseCase()
                .onSuccess { articles ->
                    // On success, update the state with the fetched articles
                    _state.update {
                        it.copy(
                            articles = articles,
                            isLoading = false,
                            currentUserRole = sessionManager.getUserRole()
                        )
                    }
                }
                .onFailure { exception ->
                    // On failure, update the state with an error message
                    _state.update {
                        it.copy(
                            error = exception.message ?: "An unknown error occurred",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onSignOutClicked() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _effect.send(NewsFeedContract.Effect.NavigateToAuth)
        }
    }
}