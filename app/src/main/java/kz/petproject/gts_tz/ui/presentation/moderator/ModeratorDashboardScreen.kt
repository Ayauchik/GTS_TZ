package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData

// --- Screen 1: Moderator's Dashboard ---

/**
 * The main screen for a moderator to see a list of articles awaiting review.
 *
 * @param articlesForModeration The list of articles with "ON_MODERATION" status.
 * @param onArticleClick Lambda triggered when a moderator clicks on an article to review it.
 * @param onNavigateUp Lambda for back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeratorDashboardScreen(
    articlesForModeration: List<Article>,
    onArticleClick: (articleId: Int) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("На модерации") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (articlesForModeration.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Все статьи проверены. Отличная работа!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(articlesForModeration, key = { it.id }) { article ->
                    Card(
                        onClick = { onArticleClick(article.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(article.title, style = MaterialTheme.typography.titleMedium)
                                Text("Автор: ${article.author.name}", style = MaterialTheme.typography.bodySmall)
                            }
                            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Проверить статью")
                        }
                    }
                }
            }
        }
    }
}


@Preview(name = "Moderator Dashboard", showBackground = true)
@Composable
fun ModeratorDashboardScreenPreview() {
    MaterialTheme {
        ModeratorDashboardScreen(
            articlesForModeration = DummyData.articles.filter { it.status == "ON_MODERATION" },
            onArticleClick = {},
            onNavigateUp = {}
        )
    }
}

@Preview(name = "Moderator Dashboard - Empty", showBackground = true)
@Composable
fun ModeratorDashboardScreenEmptyPreview() {
    MaterialTheme {
        ModeratorDashboardScreen(
            articlesForModeration = emptyList(),
            onArticleClick = {},
            onNavigateUp = {}
        )
    }
}