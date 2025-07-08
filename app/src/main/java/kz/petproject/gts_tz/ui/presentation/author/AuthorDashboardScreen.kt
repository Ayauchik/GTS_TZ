package kz.petproject.gts_tz.ui.presentation.author

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.DummyData
import kz.petproject.gts_tz.ui.model.AuthorArticleCard

/**
 * The main screen for an author to view their own articles and statuses.
 *
 * @param authorName The name of the current author.
 * @param authorArticles The list of articles written by this author.
 * @param onArticleClick Lambda for when a (rejected) article is clicked.
 * @param onFabClick Lambda for the "create new post" floating action button.
 * @param onNavigateUp Lambda for back navigation.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorDashboardScreen(
    authorArticles: List<Article>,
    onArticleClick: (articleId: String) -> Unit,
    onFabClick: () -> Unit,
    onRefresh: () -> Unit,
    isLoading: Boolean = false,
    onNavigateUp: () -> Unit
) {

    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRefresh()
        }
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои статьи") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(Icons.Default.Add, contentDescription = "Создать статью")
            }
        }
    ) { paddingValues ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {

            if (authorArticles.isEmpty() && isLoading && !pullToRefreshState.isRefreshing) {
                // Show a full-screen loader only on initial load
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (authorArticles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Вы еще не написали ни одной статьи.\nНажмите '+' чтобы начать.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(authorArticles, key = { it.id }) { article ->
                        AuthorArticleCard(
                            article = article,
                            onArticleClick = onArticleClick
                        )
                    }
                }
            }

            // 5. Place the PullToRefreshContainer at the top center of the Box
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}


// --- PREVIEWS ---
//
//@Preview(name = "Author Dashboard - With Posts", showBackground = true)
//@Composable
//fun AuthorDashboardScreenPreview_WithPosts() {
//    MaterialTheme {
//        AuthorDashboardScreen(
//            authorName = "Иван Петров",
//            // We get all articles written by author with id 1
//            authorArticles = DummyData.articles,
//            onArticleClick = {},
//            onFabClick = {},
//            onNavigateUp = {}
//        )
//    }
//}
//
//@Preview(name = "Author Dashboard - Empty State", showBackground = true)
//@Composable
//fun AuthorDashboardScreenPreview_Empty() {
//    MaterialTheme {
//        AuthorDashboardScreen(
//            authorName = "Новый Автор",
//            authorArticles = emptyList(), // No articles yet
//            onArticleClick = {},
//            onFabClick = {},
//            onNavigateUp = {}
//        )
//    }
//}