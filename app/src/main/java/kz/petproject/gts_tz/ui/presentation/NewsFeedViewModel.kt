package kz.petproject.gts_tz.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.data.local.SessionManager

class NewsFeedViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(NewsFeedContract.State())
    val state = _state.asStateFlow()

    private val _effect = Channel<NewsFeedContract.Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            // In a real app, this would be a repository call to fetch articles.
            val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
            _state.update { it.copy(articles = publishedArticles, isLoading = false) }
        }
    }

    fun onSignOutClicked() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _effect.send(NewsFeedContract.Effect.NavigateToAuth)
        }
    }
}