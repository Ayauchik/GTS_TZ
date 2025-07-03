package kz.petproject.gts_tz.ui.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.data.User

// --- Helper UI Components ---

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

// --- Main Screen ---

/**
 * The screen for an administrator to manage users.
 *
 * @param users List of all users in the system.
 * @param onCreateUser Lambda triggered to create a new user with a given name and role.
 * @param onNavigateUp Lambda for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    users: List<User>,
    onCreateUser: (name: String, login: String, password: String, role: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var newUserName by remember { mutableStateOf("") }
    var newUserLogin by remember { mutableStateOf("") }
    var newUserPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val roles = listOf("AUTHOR", "MODERATOR")
    var selectedRole by remember { mutableStateOf<String?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Панель администратора") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {  paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- User Creation Form ---
            Text("Создать нового пользователя", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = newUserName,
                onValueChange = { newUserName = it },
                label = { Text("Имя и фамилия") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = newUserLogin,
                onValueChange = { newUserLogin = it },
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = newUserPassword,
                onValueChange = { newUserPassword = it },
                label = { Text("Пароль") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Favorite else Icons.Filled.FavoriteBorder
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Показать/скрыть пароль")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedRole?.replaceFirstChar { it.titlecase() } ?: "Выберите роль",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Роль") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
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
                                selectedRole = role
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    onCreateUser(newUserName, newUserLogin, newUserPassword, selectedRole!!)
                    // Reset form
                    newUserName = ""
                    newUserLogin = ""
                    newUserPassword = ""
                    selectedRole = null
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = newUserName.isNotBlank() && newUserLogin.isNotBlank() && newUserPassword.isNotBlank() && selectedRole != null
            ) {
                Text("Создать пользователя")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // --- Existing Users List ---
            Text("Существующие пользователи", style = MaterialTheme.typography.titleLarge)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(users, key = { it.id }) { user ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(user.name, style = MaterialTheme.typography.bodyLarge)
                                Text(user.login, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            RoleChip(user.role)
                        }
                    }
                }
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "Admin Dashboard", showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    MaterialTheme {
        AdminDashboardScreen(
            users = DummyData.users,
            onCreateUser = { _, _, _, _ -> },
            onNavigateUp = {}
        )
    }
}