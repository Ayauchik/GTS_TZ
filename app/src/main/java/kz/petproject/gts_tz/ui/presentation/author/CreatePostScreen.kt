package kz.petproject.gts_tz.ui.presentation.author

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A stateless screen for creating or editing an article.
 *
 * @param title The current value for the article title.
 * @param onTitleChange Lambda called when the title text changes.
 * @param content The current value for the article content.
 * @param onContentChange Lambda called when the content text changes.
 * @param isEditing A boolean flag to indicate if the screen is in "edit" mode.
 *                  This changes the UI text (e.g., "New Post" vs "Edit Post").
 * @param onSubmitClick Lambda called when the user clicks the submit button.
 * @param onNavigateUp Lambda for handling back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    isEditing: Boolean,
    onSubmitClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Редактирование статьи" else "Новая статья") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Makes the column scrollable
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Field
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Заголовок") },
                singleLine = true
            )

            // Content Field
            OutlinedTextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp), // Give it a good minimum height
                label = { Text("Текст статьи") }
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // Submit Button
            Button(
                onClick = onSubmitClick,
                modifier = Modifier.fillMaxWidth(),
                // Button is disabled if title or content is blank
                enabled = title.isNotBlank() && content.isNotBlank()
            ) {
                Text(
                    text = if (isEditing) "Сохранить изменения" else "Отправить на модерацию",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

// --- PREVIEWS ---

/**
 * Stateful preview for creating a new post.
 * This pattern allows you to interact with the UI in the preview pane.
 */
@Preview(name = "Create New Post", showBackground = true)
@Composable
fun CreatePostScreenPreview_New() {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    MaterialTheme {
        CreatePostScreen(
            title = title,
            onTitleChange = { title = it },
            content = content,
            onContentChange = { content = it },
            isEditing = false,
            onSubmitClick = { /* Clicked! */ },
            onNavigateUp = { /* Nav up! */ }
        )
    }
}

/**
 * Stateful preview for editing an existing post.
 */
@Preview(name = "Edit Existing Post", showBackground = true)
@Composable
fun CreatePostScreenPreview_Editing() {
    var title by remember { mutableStateOf("Архитектура MVVM") }
    var content by remember { mutableStateOf("MVVM (Model-View-ViewModel) - это...") }

    MaterialTheme {
        CreatePostScreen(
            title = title,
            onTitleChange = { title = it },
            content = content,
            onContentChange = { content = it },
            isEditing = true,
            onSubmitClick = { /* Clicked! */ },
            onNavigateUp = { /* Nav up! */ }
        )
    }
}