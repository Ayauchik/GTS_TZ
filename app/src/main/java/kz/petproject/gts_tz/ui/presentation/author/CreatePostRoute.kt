package kz.petproject.gts_tz.ui.presentation.author

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatePostRoute(
    navController: NavController,
    viewModel: CreatePostViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CreatePostEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                is CreatePostEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    // Note: The Scaffold for the snackbar should be here or in CreatePostScreen
    // For simplicity, we're just passing the logic down.
    CreatePostScreen(
        title = state.title,
        onTitleChange = viewModel::onTitleChange,
        content = state.content,
        onContentChange = viewModel::onContentChange,
        isEditing = state.isEditing,
        onSaveClick = viewModel::onSaveClicked,
        onSubmitClick = viewModel::onSubmitClicked,
        onNavigateUp = { navController.popBackStack() },
        isLoading = state.isLoading,
        status = state.status ?: "DRAFT"
    )
}