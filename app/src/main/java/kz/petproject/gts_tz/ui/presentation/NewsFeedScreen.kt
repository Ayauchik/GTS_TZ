package kz.petproject.gts_tz.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen(
    articles: List<Article>,
    onArticleClick: (articleId: Int) -> Unit,
    currentUserRole: String? = null, // Can be null for a general user
    onFabClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Новости") })
        },
        // --- NEW FLOATING ACTION BUTTON LOGIC ---
        floatingActionButton = {
            // Only show the FAB if the current user is an Author
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
                modifier = Modifier.fillMaxSize().padding(paddingValues),
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

// --- UPDATED & NEW PREVIEWS ---

@Preview(name = "News Feed (Public User)", showBackground = true)
@Composable
fun NewsFeedScreenPreview_Public() {
    MaterialTheme {
        val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
        NewsFeedScreen(
            articles = publishedArticles,
            onArticleClick = {},
            currentUserRole = null // This user is not an author, so no FAB
        )
    }
}

// --- NEW PREVIEW ---
@Preview(name = "News Feed (As Author)", showBackground = true)
@Composable
fun NewsFeedScreenPreview_AsAuthor() {
    MaterialTheme {
        val publishedArticles = DummyData.articles.filter { it.status == "PUBLISHED" }
        NewsFeedScreen(
            articles = publishedArticles,
            onArticleClick = {},
            currentUserRole = "AUTHOR", // This user IS an author, so the FAB will appear
            onFabClick = {}
        )
    }
}

@Preview(name = "News Feed Empty State", showBackground = true)
@Composable
fun NewsFeedScreenPreview_Empty() {
    MaterialTheme {
        NewsFeedScreen(
            articles = emptyList(),
            onArticleClick = {}
        )
    }
}