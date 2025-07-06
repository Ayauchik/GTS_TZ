package kz.petproject.gts_tz.ui.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kz.petproject.gts_tz.data.local.TokenManager
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SplashScreen(navController: NavController, tokenManager: TokenManager = koinInject()) {
    LaunchedEffect(key1 = true) {
        delay(1000) // Simulate a loading delay
        val token = tokenManager.getToken()
        val destination = if (token.isNullOrBlank()) "auth" else "news_feed"
        navController.navigate(destination) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}