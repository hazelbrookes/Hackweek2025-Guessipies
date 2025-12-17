package com.mobiedev.search.guessipies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.network.RecipesFetcher
import com.mobiedev.search.guessipies.ui.screens.GameScreen
import com.mobiedev.search.guessipies.ui.screens.HomeScreen
import com.mobiedev.search.guessipies.ui.screens.HowToScreen
import com.mobiedev.search.guessipies.ui.screens.ResultsScreen
import com.mobiedev.search.guessipies.ui.screens.ScoresScreen
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import com.mobiedev.search.guessipies.viewmodel.GameViewModel
import kotlinx.serialization.Serializable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

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

class RetryOn500Interceptor(private val maxRetries: Int = 5) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        var tryCount = 0
        while (response.code == 500 && tryCount < maxRetries) {
            response.close()
            tryCount++
            response = chain.proceed(chain.request())
        }
        return response
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val okHttpClient = OkHttpClient.Builder().addInterceptor(RetryOn500Interceptor()).build()
        val recipesFetcher = RecipesFetcher(okHttpClient)
        val gameViewModel = GameViewModel(recipesFetcher)

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
                                onClick = { navController.navigate(Home) },
                                icon = { Icon(Icons.Default.Home, "Home Icon") },
                                label = { Text("Home") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Howto::class.qualifiedName,
                                onClick = { navController.navigate(Howto) },
                                icon = { Icon(Icons.Default.Info, "Game Information Icon") },
                                label = { Text("Rules") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == Scores::class.qualifiedName,
                                onClick = { navController.navigate(Scores) },
                                icon = { Icon(Icons.Default.AccountCircle, "Scores Icon") },
                                label = { Text("Scores") }
                            )
                        }
                    }
                ) { innerPadding ->
                    GuessipiesAppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        gameViewModel = gameViewModel
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
    gameViewModel: GameViewModel
) {
    val context = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onNavigateToGame = {
                    gameViewModel.resetGameState()
                    navController.navigate(route = Game)
                }
            )
        }
        composable<Game> {
            GameScreen(
                viewModel = gameViewModel,
                onClickOpenRecipe = { recipeId ->
                    openRecipeLink(
                        context = context,
                        recipeId = recipeId
                    )
                },
                onNavigateToResults = {
                    navController.navigate(route = Results)
                }
            )
        }
        composable<Howto> {
            HowToScreen()
        }
        composable<Scores> {
            ScoresScreen(
                onNavigateToHome= { navController.navigate(route = Home) }
            )
        }
        composable<Results> {
            ResultsScreen(
                gameViewModel,
                onClickOpenRecipe = { recipeId ->
                    openRecipeLink(
                        context = context,
                        recipeId = recipeId
                    )
                },
                onNavigateToGame = {
                    navController.navigate(route = Game)
                }
            )
        }
    }
}

private fun openRecipeLink(context: Context, recipeId: String) {
    val url = "https://www.bbc.co.uk/food/recipes/$recipeId"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}