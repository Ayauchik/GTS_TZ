package kz.petproject.gts_tz.ui.presentation.author

import kz.petproject.gts_tz.data.Article

/**
 * State holder for the Author Dashboard screen.
 *
 * @param articles The list of articles belonging to the author.
 * @param isLoading True if articles are currently being fetched.
 * @param error An optional error message if the fetch fails.
 */
data class AuthorDashboardState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)


