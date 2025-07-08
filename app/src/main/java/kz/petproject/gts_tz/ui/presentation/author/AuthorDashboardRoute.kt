package kz.petproject.gts_tz.ui.presentation.author

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorDashboardRoute(
    navController: NavController,
    viewModel: AuthorDashboardViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val onArticleClick: (String) -> Unit = { articleId ->
        navController.navigate("create_post_screen/$articleId")
    }

    val onFabClick: () -> Unit = {
        navController.navigate("create_post_screen/new")
    }

    AuthorDashboardScreen(
        authorArticles = state.articles,
        onArticleClick = onArticleClick,
        onFabClick = onFabClick,
        onNavigateUp = { navController.popBackStack() },
        onRefresh = viewModel::onRefresh,
        isLoading = state.isLoading
    )
}