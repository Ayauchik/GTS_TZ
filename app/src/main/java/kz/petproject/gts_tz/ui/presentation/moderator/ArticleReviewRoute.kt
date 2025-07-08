package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArticleReviewRoute(
    navController: NavController,
    viewModel: ArticleReviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ArticleReviewEffect.RefreshAndNavigateBack -> {
                    navController.popBackStack()
                }
                is ArticleReviewEffect.ShowSnackbar -> {
                    // TODO: Implement a snackbar host if desired
                }
            }
        }
    }

    state.article?.let { article ->
        ArticleReviewScreen(
            article = article,
            onApprove = viewModel::onApproveClicked,
            onRejectRequest = viewModel::onRejectClicked, // Shows the dialog
            showRejectDialog = state.showRejectDialog,
            onRejectConfirm = viewModel::onRejectConfirmed,
            onRejectDismiss = viewModel::onDialogDismiss,
            onNavigateUp = { navController.popBackStack() },
            isLoading = state.isLoading
        )
    }
}