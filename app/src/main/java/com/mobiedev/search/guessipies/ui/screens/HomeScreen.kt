package com.mobiedev.search.guessipies.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.GuessipiesAppNavigation
import com.mobiedev.search.guessipies.randomRecipeInfo
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onNavigateToGame: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Guessipies",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.titleLarge
        )
        Button(
            onClick ={ onNavigateToGame() }
        ) {
            Text("Play")
        }
        Button({
            coroutineScope.launch {
                val recipe = randomRecipeInfo()
                Log.d("MILK", recipe.second[0])
            }
        }) {
            Text("Feedback")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    GuessipiesTheme {
        HomeScreen(
            onNavigateToGame = {}
        )
    }
}