package kz.petproject.gts_tz.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.ui.model.ArticleCard

/**
 * The main news feed screen, accessible to all users.
 * @param articles The list of published articles to display.
 * @param onArticleClick Lambda triggered when a user clicks on an article card.
 * @param currentUserRole The role of the currently logged-in user (e.g., "AUTHOR"). Null if anonymous.
 * @param onFabClick Lambda for the FAB's click action.
 * @param onSignOutClick Lambda for the sign-out button click action.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen(
    articles: List<Article>,
    onArticleClick: (articleId: String) -> Unit,
    currentUserRole: String? = null,
    onFabClick: () -> Unit = {},
    onSignOutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Новости") },
                actions = {
                    IconButton(onClick = onSignOutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Выйти"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (currentUserRole == "AUTHOR") {
                FloatingActionButton(
                    onClick = onFabClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Создать новую статью"
                    )
                }
            }
        }
    ) { paddingValues ->
        if (articles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Пока нет опубликованных новостей.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(articles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article.id) }
                    )
                }
            }
        }
    }
}

// --- UPDATED PREVIEWS ---

@Preview(name = "News Feed (Public User)", showBackground = true)
@Composable
fun NewsFeedScreenPreview_Public() {
    MaterialTheme {
        val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
        NewsFeedScreen(
            articles = publishedArticles,
            onArticleClick = {},
            currentUserRole = null,
            onSignOutClick = {}
        )
    }
}

@Preview(name = "News Feed (As Author)", showBackground = true)
@Composable
fun NewsFeedScreenPreview_AsAuthor() {
    MaterialTheme {
        val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
        NewsFeedScreen(
            articles = publishedArticles,
            onArticleClick = {},
            currentUserRole = "AUTHOR",
            onFabClick = {},
            onSignOutClick = {}
        )
    }
}

@Preview(name = "News Feed Empty State", showBackground = true)
@Composable
fun NewsFeedScreenPreview_Empty() {
    MaterialTheme {
        NewsFeedScreen(
            articles = emptyList(),
            onArticleClick = {},
            onSignOutClick = {}
        )
    }
}