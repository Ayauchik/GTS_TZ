package kz.petproject.gts_tz.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kz.petproject.gts_tz.ui.presentation.AuthContract
import kz.petproject.gts_tz.ui.presentation.AuthScreen
import kz.petproject.gts_tz.ui.presentation.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthRoute(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthContract.Effect.NavigateToMain -> {
                    onLoginSuccess()
                }
            }
        }
    }

    AuthScreen(
        login = state.loginInput,
        password = state.passwordInput,
        onLoginChange = viewModel::onLoginChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onLoginClick = viewModel::onLoginClicked,
        isLoading = state.isLoading,
        error = state.error
    )
}