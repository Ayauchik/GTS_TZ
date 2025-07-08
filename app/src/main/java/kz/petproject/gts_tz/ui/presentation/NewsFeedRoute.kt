package kz.petproject.gts_tz.ui.presentation.news_feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import kz.petproject.gts_tz.ui.presentation.NewsFeedContract
import kz.petproject.gts_tz.ui.presentation.NewsFeedScreen
import kz.petproject.gts_tz.ui.presentation.NewsFeedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsFeedRoute(
    // It no longer needs the NavController directly
    onNavigateToAuth: () -> Unit,
    viewModel: NewsFeedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsFeedContract.Effect.NavigateToAuth -> {
                    // Call the hoisted lambda instead of navigating directly
                    onNavigateToAuth()
                }
            }
        }
    }

    NewsFeedScreen(
        articles = state.articles,
        onArticleClick = { /* TODO: Implement navigation to article details */ },
        currentUserRole = state.currentUserRole,
        onFabClick = { /* TODO: Implement navigation to create post */ },
        onSignOutClick = viewModel::onSignOutClicked
    )
}