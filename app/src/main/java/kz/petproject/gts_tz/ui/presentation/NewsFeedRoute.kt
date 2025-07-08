package kz.petproject.gts_tz.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kz.petproject.gts_tz.ui.presentation.NewsFeedContract
import kz.petproject.gts_tz.ui.presentation.NewsFeedScreen
import kz.petproject.gts_tz.ui.presentation.NewsFeedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsFeedRoute(
    onNavigateToAuth: () -> Unit,
    viewModel: NewsFeedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsFeedContract.Effect.NavigateToAuth -> {
                    onNavigateToAuth()
                }
            }
        }
    }

    NewsFeedScreen(
        articles = state.articles,
        isLoading = state.isLoading,
        currentUserRole = state.currentUserRole,
        onArticleClick = { /* TODO: Navigate to article detail screen */ },
        onSignOutClick = viewModel::onSignOutClicked,
        onRefresh = viewModel::onRefresh // Pass the refresh lambda to the UI
    )
}