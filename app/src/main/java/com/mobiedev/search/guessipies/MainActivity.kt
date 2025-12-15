package com.mobiedev.search.guessipies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobiedev.search.guessipies.ui.screens.GameScreen
import com.mobiedev.search.guessipies.ui.screens.HomeScreen
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Howto

@Serializable
object Game

@Serializable
object Result

@Serializable
object Scores

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessipiesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GuessipiesAppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GuessipiesAppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onNavigateToGame= { navController.navigate(route = Game) }
            )
        }
        composable<Game> {
            GameScreen(
                onNavigateToHome= { navController.navigate(route = Home) }
            )
        }
    }
}