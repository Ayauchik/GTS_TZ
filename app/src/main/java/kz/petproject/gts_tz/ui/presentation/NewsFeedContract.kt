package kz.petproject.gts_tz.ui.presentation

import kz.petproject.gts_tz.data.Article

object NewsFeedContract {
    data class State(
        val articles: List<Article> = emptyList(),
        val isLoading: Boolean = true,
        val currentUserRole: String? = null // TODO: This should come from a user session
    )

    sealed class Effect {
        data object NavigateToAuth : Effect()
    }
}