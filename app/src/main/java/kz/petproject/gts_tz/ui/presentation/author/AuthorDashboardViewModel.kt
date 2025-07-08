package kz.petproject.gts_tz.ui.presentation.author

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.domain.use_cases.GetMyArticlesUseCase

/** ViewModel for the Author Dashboard.It handles fetching and displaying the author's articles.
 *
 * @param getMyArticlesUseCase The use case to fetch the articles from the repository .
 */
class AuthorDashboardViewModel(
    private val getMyArticlesUseCase: GetMyArticlesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthorDashboardState())
    val state = _state.asStateFlow()

    init {
        loadArticles()
    }

    fun onRefresh(){
        loadArticles()
    }

    /**
     * Fetches the list of articles for the current author from the server.
     */
    private fun loadArticles() {
        viewModelScope.launch {
            // Set loading state to true before the network call
            _state.update { it.copy(isLoading = true, error = null) }

            getMyArticlesUseCase()
                .onSuccess { articles ->
                    // On success, update the state with the fetched articles and stop loading
                    _state.update {
                        it.copy(
                            articles = articles.sortedByDescending { article -> article.createdAt },
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    // On failure, update the state with an error message and stop loading
                    _state.update {
                        it.copy(
                            error = exception.message ?: "An unknown error occurred",
                            isLoading = false
                        )
                    }
                }
        }
    }
}