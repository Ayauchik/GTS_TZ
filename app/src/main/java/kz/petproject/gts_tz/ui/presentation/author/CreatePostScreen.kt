package kz.petproject.gts_tz.ui.presentation.author

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
 * @param onSaveClick Lambda called when the user clicks the save draft button.
 * @param onSubmitClick Lambda called when the user clicks the submit button.
 * @param onNavigateUp Lambda for handling back navigation.
 * @param isLoading True if a network operation is in progress.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    isEditing: Boolean,
    status: String,
    isLoading: Boolean,
    showDeleteDialog: Boolean, // <-- New parameter
    onSaveClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onDeleteClick: () -> Unit, // <-- New parameter
    onDeleteConfirm: () -> Unit, // <-- New parameter
    onDeleteDismiss: () -> Unit, // <-- New parameter
    onNavigateUp: () -> Unit
) {

    if (showDeleteDialog) {
        DeleteConfirmDialog(
            onDismiss = onDeleteDismiss,
            onConfirm = onDeleteConfirm
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Редактирование статьи" else "Новая статья") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp, enabled = !isLoading) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    val isDeletable = isEditing && status in listOf("DRAFT", "REJECTED")
                    if (isDeletable) {
                        IconButton(onClick = onDeleteClick, enabled = !isLoading) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Article",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Заголовок") },
                singleLine = true,
                enabled = !isLoading
            )

            OutlinedTextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp),
                label = { Text("Текст статьи") },
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth().wrapContentWidth())
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onSaveClick,
                        modifier = Modifier.weight(1f),
                        enabled = title.isNotBlank() && content.isNotBlank()
                    ) {
                        Text(text = "Сохранить как черновик")
                    }

                    val isSubmittable = status in listOf("DRAFT", "REJECTED")

                    Button(
                        onClick = onSubmitClick,
                        modifier = Modifier.weight(1f),
                        // Disable if fields are blank OR if the status is not submittable
                        enabled = title.isNotBlank() && content.isNotBlank() && isSubmittable
                    ) {
                        Text(text = "На модерацию")
                    }
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to permanently delete this article?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

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
            status = "DRAFT",
            isLoading = false,
            showDeleteDialog = false,
            onSaveClick = {},
            onSubmitClick = {},
            onDeleteClick = {},
            onDeleteConfirm = {},
            onDeleteDismiss = {},
            onNavigateUp = {}
        )
    }
}