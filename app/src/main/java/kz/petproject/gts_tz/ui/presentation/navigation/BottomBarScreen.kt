package kz.petproject.gts_tz.ui.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kz.petproject.gts_tz.ui.presentation.MainViewModel
import kz.petproject.gts_tz.ui.presentation.admin.AdminDashboardRoute
import kz.petproject.gts_tz.ui.presentation.author.AuthorDashboardRoute
import kz.petproject.gts_tz.ui.presentation.news_feed.NewsFeedRoute
import org.koin.androidx.compose.koinViewModel

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object NewsFeed : BottomBarScreen("news_feed", "Новости", Icons.Default.Home)
    object AdminPanel : BottomBarScreen("admin_panel", "Админ", Icons.Default.AccountBox)
    object AuthorDashboard : BottomBarScreen("author_dashboard", "Мои статьи", Icons.Default.Create)
}

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = koinViewModel()
) {
    val userRole by viewModel.userRole.collectAsState()
    val bottomBarNavController = rememberNavController()

    val bottomBarScreens = when (userRole) {
        "ADMIN" -> listOf(BottomBarScreen.NewsFeed, BottomBarScreen.AdminPanel)
        "AUTHOR" -> listOf(BottomBarScreen.NewsFeed, BottomBarScreen.AuthorDashboard)
        "MODERATOR" -> listOf(BottomBarScreen.NewsFeed) // TODO: Add Moderator screen
        else -> listOf(BottomBarScreen.NewsFeed)
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by bottomBarNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar {
                bottomBarScreens.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(text = screen.title) },
                        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            bottomBarNavController.navigate(screen.route) {
                                popUpTo(bottomBarNavController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomBarNavController,
            startDestination = BottomBarScreen.NewsFeed.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomBarScreen.NewsFeed.route) {
                NewsFeedRoute(
                    onNavigateToAuth = {
                        navController.navigate("auth") {
                            popUpTo("main_screen") { inclusive = true }
                        }
                    }
                )
            }
            composable(route = BottomBarScreen.AdminPanel.route) { AdminDashboardRoute() }
            composable(route = BottomBarScreen.AuthorDashboard.route) {
                // Pass the top-level nav controller for navigating outside the bottom bar
                AuthorDashboardRoute(navController = navController)
            }
        }
    }
}