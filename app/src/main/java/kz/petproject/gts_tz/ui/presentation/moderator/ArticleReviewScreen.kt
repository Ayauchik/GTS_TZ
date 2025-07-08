package kz.petproject.gts_tz.ui.presentation.moderator

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
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData

/**
 * A screen where a moderator can read a full article and approve or reject it.
 *
 * @param article The article to be reviewed.
 * @param onApprove Lambda for the "Approve" action.
 * @param onReject Lambda for the "Reject" action, which takes the reason as a String.
 * @param onNavigateUp Lambda for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleReviewScreen(
    article: Article,
    onApprove: () -> Unit,
    onReject: (reason: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var showRejectDialog by remember { mutableStateOf(false) }

    if (showRejectDialog) {
        RejectReasonDialog(
            onDismiss = { showRejectDialog = false },
            onConfirm = { reason ->
                showRejectDialog = false
                onReject(reason)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Проверка статьи") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Scrollable content area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(article.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Автор: ${article.author}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Divider(Modifier.padding(vertical = 16.dp))
                Text(article.content, style = MaterialTheme.typography.bodyLarge)
            }

            // Action buttons at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { showRejectDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Отклонить")
                }
                Button(
                    onClick = onApprove,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Одобрить")
                }
            }
        }
    }
}

// --- Helper Component: Rejection Dialog ---

/**
 * A dialog for entering the reason for rejecting an article.
 *
 * @param onDismiss Lambda called when the dialog is dismissed (e.g., by clicking outside or cancel).
 * @param onConfirm Lambda called with the reason text when the confirm button is clicked.
 */
@Composable
private fun RejectReasonDialog(
    onDismiss: () -> Unit,
    onConfirm: (reason: String) -> Unit
) {
    var reason by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Причина отклонения") },
        text = {
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Укажите причину") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(reason) },
                enabled = reason.isNotBlank() // Cannot confirm with an empty reason
            ) {
                Text("Отклонить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}


@Preview(name = "Article Review Screen", showBackground = true)
@Composable
fun ArticleReviewScreenPreview() {
    MaterialTheme {
        ArticleReviewScreen(
            article = DummyData.articles.first { it.status == "ON_MODERATION" },
            onApprove = {},
            onReject = {},
            onNavigateUp = {}
        )
    }
}

@Preview(name = "Rejection Reason Dialog", showBackground = true)
@Composable
fun RejectReasonDialogPreview() {
    MaterialTheme {
        // We place it in a Box to simulate how it looks on top of a screen
        Box(Modifier.fillMaxSize()) {
            RejectReasonDialog(onDismiss = {}, onConfirm = {})
        }
    }
}