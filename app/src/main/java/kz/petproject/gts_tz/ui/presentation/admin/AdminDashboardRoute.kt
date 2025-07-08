package kz.petproject.gts_tz.ui.presentation.admin

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdminDashboardRoute(
    viewModel: AdminViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }
    LaunchedEffect(state.successMessage) {
        state.successMessage?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    AdminDashboardScreen(
        state = state,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        onNameChange = viewModel::onNameChange,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRoleChange = viewModel::onRoleChange,
        onCreateUser = viewModel::onCreateUser,
        onRefresh = viewModel::onRefresh, // <-- Pass the refresh function
        onNavigateUp = { /* In Bottom Nav, this is not typically used */ }
    )
}