package kz.petproject.gts_tz.ui.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.R
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.data.User

@Composable
private fun RoleChip(role: String) {
    val (backgroundColor, textColor) = when (role) {
        "ADMIN" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        "AUTHOR" -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        "MODERATOR" -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    val roleText = when (role) {
        "ADMIN" -> "Админ"
        "AUTHOR" -> "Автор"
        "MODERATOR" -> "Модератор"
        else -> "Неизвестно"
    }

    Text(
        text = roleText,
        color = textColor,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    state: AdminContract.State,
    snackbarHost: @Composable () -> Unit,
    onNameChange: (String) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRoleChange: (String) -> Unit,
    onCreateUser: () -> Unit,
    onRefresh: () -> Unit, // <-- New parameter for refreshing
    onNavigateUp: () -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val roles = listOf("AUTHOR", "MODERATOR")
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // 1. Remember the pull-to-refresh state
    val pullToRefreshState = rememberPullToRefreshState()

    // 2. Trigger the refresh action when the user pulls
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }

    // 3. Stop the refresh animation when loading is finished
    LaunchedEffect(state.isLoading) {
        if (!state.isLoading) {
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Панель администратора") },
            )
        },
        snackbarHost = snackbarHost
    ) { paddingValues ->
        // 4. Wrap the content in a Box and apply the nestedScroll modifier
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            if (state.isLoading && state.users.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // --- User Creation Form as a single lazy item ---
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text(
                                "Создать нового пользователя",
                                style = MaterialTheme.typography.titleLarge
                            )

                            OutlinedTextField(
                                value = state.newUserName,
                                onValueChange = onNameChange,
                                label = { Text("Имя и фамилия") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = state.newUserLogin,
                                onValueChange = onLoginChange,
                                label = { Text("Логин") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            val showIcon = painterResource(R.drawable.closed_eye)
                            val hideIcon = painterResource(R.drawable.opened_eye)

                            OutlinedTextField(
                                value = state.newUserPassword,
                                onValueChange = onPasswordChange,
                                label = { Text("Пароль") },
                                singleLine = true,
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    val image = if (passwordVisible) hideIcon else showIcon
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            painter = image,
                                            contentDescription = "Показать/скрыть пароль",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            ExposedDropdownMenuBox(
                                expanded = isDropdownExpanded,
                                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                            ) {
                                OutlinedTextField(
                                    value = state.selectedRole?.replaceFirstChar { it.titlecase() }
                                        ?: "Выберите роль",
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Роль") },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = isDropdownExpanded
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = isDropdownExpanded,
                                    onDismissRequest = { isDropdownExpanded = false }
                                ) {
                                    roles.forEach { role ->
                                        DropdownMenuItem(
                                            text = { Text(role.replaceFirstChar { it.titlecase() }) },
                                            onClick = {
                                                onRoleChange(role)
                                                isDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = onCreateUser,
                                modifier = Modifier.fillMaxWidth(),
                                // Button is disabled if the form is invalid OR if any loading operation is happening.
                                enabled = state.newUserName.isNotBlank() && state.newUserLogin.isNotBlank() && state.newUserPassword.isNotBlank() && state.selectedRole != null && !state.isLoading
                            ) {
                                // Show the spinner inside the button ONLY during the create user action,
                                // not during the initial user list fetch.
                                if (state.isLoading && state.users.isNotEmpty()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                } else {
                                    Text("Создать пользователя")
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                            Text(
                                "Существующие пользователи",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    // --- Existing Users List ---
                    items(state.users, key = { it.id }) { user ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(user.name, style = MaterialTheme.typography.bodyLarge)
                                    Text(
                                        user.login,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                RoleChip(user.role)
                            }
                        }
                    }
                }
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

        }
    }
}


@Preview(name = "Admin Dashboard", showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    MaterialTheme {
        AdminDashboardScreen(
            state = AdminContract.State(users = DummyData.users, isLoading = false),
            snackbarHost = {},
            onNameChange = {},
            onLoginChange = {},
            onPasswordChange = {},
            onRoleChange = {},
            onCreateUser = {},
            onNavigateUp = {},
            onRefresh = {}
        )
    }
}