package kz.petproject.gts_tz.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A stateless authentication screen.
 * It is designed to be controlled by a ViewModel.
 *
 * @param login The current value for the login field.
 * @param password The current value for the password field.
 * @param onLoginChange Lambda to be called when the login text changes.
 * @param onPasswordChange Lambda to be called when the password text changes.
 * @param onLoginClick Lambda to be called when the login button is clicked.
 * @param isLoading True if the login process is in progress.
 * @param error An optional error message to display.
 */
@Composable
fun AuthScreen(
    login: String,
    password: String,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
    error: String?
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Авторизация",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Login Field
        OutlinedTextField(
            value = login,
            onValueChange = onLoginChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Логин") },
            singleLine = true,
            isError = error != null // Highlight field if there is any error
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder

                val description = if (passwordVisible) "Скрыть пароль" else "Показать пароль"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            isError = error != null
        )

        // Error Message
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button or Progress Indicator
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = login.isNotBlank() && password.isNotBlank()
            ) {
                Text("Войти", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Default State", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_Default() {
    MaterialTheme { // Wrap in a theme for proper previewing
        AuthScreen(
            login = "",
            password = "",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = false,
            error = null
        )
    }
}

@Preview(name = "With Input", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_WithInput() {
    MaterialTheme {
        AuthScreen(
            login = "author_1",
            password = "password123",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = false,
            error = null
        )
    }
}

@Preview(name = "Loading State", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_Loading() {
    MaterialTheme {
        AuthScreen(
            login = "author_1",
            password = "password123",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = true,
            error = null
        )
    }
}

@Preview(name = "Error State", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AuthScreenPreview_Error() {
    MaterialTheme {
        AuthScreen(
            login = "author_1",
            password = "wrongpassword",
            onLoginChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            isLoading = false,
            error = "Неверный логин или пароль"
        )
    }
}