package kz.petproject.gts_tz.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.petproject.gts_tz.ui.presentation.NewsFeedRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("auth") {
            AuthRoute(
                onLoginSuccess = {
                    navController.navigate("news_feed") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("news_feed") {
            NewsFeedRoute(navController = navController)
        }
    }
}