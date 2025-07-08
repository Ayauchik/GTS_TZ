package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ModeratorDashboardRoute(
    navController: NavController,
    viewModel: ModeratorDashboardViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    ModeratorDashboardScreen(
        articlesForModeration = state.articles,
        onArticleClick = { articleId ->
            navController.navigate("article_review/$articleId")
        },
        onRefresh = viewModel::onRefresh,
        isLoading = state.isLoading,
    )
}