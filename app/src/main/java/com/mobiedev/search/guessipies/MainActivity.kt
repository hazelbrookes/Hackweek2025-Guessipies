package com.mobiedev.search.guessipies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobiedev.search.guessipies.ui.screens.GameScreen
import com.mobiedev.search.guessipies.ui.screens.HomeScreen
import com.mobiedev.search.guessipies.ui.screens.HowToScreen
import com.mobiedev.search.guessipies.ui.screens.ScoresScreen
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Howto

@Serializable
object Results

@Serializable
object Scores

@Serializable
object Game


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            GuessipiesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                            NavigationBarItem(
                                selected = currentRoute == Home::class.qualifiedName,
                                onClick = { navController.navigate("Home") },
                                icon = { Icon(Icons.Default.Home, "Home Icon") },
                                label = { Text("Home") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Game::class.qualifiedName,
                                onClick = { navController.navigate("Game") },
                                icon = { Icon(Icons.Default.PlayArrow, "Game Icon") },
                                label = { Text("Play") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Howto::class.qualifiedName,
                                onClick = { navController.navigate("Howto") },
                                icon = { Icon(Icons.Default.Info, "Game Information Icon") },
                                label = { Text("Rules") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Scores::class.qualifiedName,
                                onClick = { navController.navigate("Scores") },
                                icon = { Icon(Icons.Default.AccountCircle, "Scores Icon") },
                                label = { Text("Scores") }
                            )
                        }
                    }
                ) { innerPadding ->
                    GuessipiesAppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun GuessipiesAppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") {
            HomeScreen(
                onNavigateToGame = { navController.navigate(route = Game) }
            )
        }
        composable("Game") {
            GameScreen(
                onNavigateToHome= { navController.navigate(route = Home) }
            )
        }
        composable("Howto") {
            HowToScreen()
        }
        composable("Scores") {
            ScoresScreen(
                onNavigateToHome= { navController.navigate(route = Home) }
            )
        }
    }
}