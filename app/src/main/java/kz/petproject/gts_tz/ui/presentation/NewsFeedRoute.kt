package kz.petproject.gts_tz.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import kz.petproject.gts_tz.ui.presentation.NewsFeedScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsFeedRoute(
    navController: NavController,
    viewModel: NewsFeedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsFeedContract.Effect.NavigateToAuth -> {
                    navController.navigate("auth") {
                        // Clear the entire back stack to prevent going back to a logged-in state
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        // Ensure there's only one instance of the auth screen
                        launchSingleTop = true
                    }
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