package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.domain.use_cases.GetArticlesForModerationUseCase

data class ModeratorDashboardState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class ModeratorDashboardViewModel(
    private val getArticlesForModerationUseCase: GetArticlesForModerationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ModeratorDashboardState())
    val state = _state.asStateFlow()

    init {
        loadQueue()
    }

    fun onRefresh() {
        loadQueue()
    }

    private fun loadQueue() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getArticlesForModerationUseCase()
                .onSuccess { articles ->
                    _state.update { it.copy(articles = articles, isLoading = false) }
                }
                .onFailure { exception ->
                    _state.update { it.copy(error = exception.message, isLoading = false) }
                }
        }
    }
}