package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobiedev.search.guessipies.GuessipiesAppNavigation
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme

@Composable
fun HomeScreen(
    onNavigateToGame: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Home Screen")
        Button({ onNavigateToGame() }) {
            Text("Play Game")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    GuessipiesTheme {
        HomeScreen {  }
    }
}