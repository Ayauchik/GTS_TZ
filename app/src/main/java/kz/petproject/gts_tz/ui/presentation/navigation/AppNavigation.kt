package kz.petproject.gts_tz.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kz.petproject.gts_tz.ui.presentation.author.CreatePostRoute
import kz.petproject.gts_tz.ui.presentation.main.MainScreen

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
                    navController.navigate("main_screen") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("main_screen") {
            MainScreen(navController = navController)
        }
        composable(
            route = "create_post_screen/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) {
            CreatePostRoute(navController = navController)
        }
    }
}