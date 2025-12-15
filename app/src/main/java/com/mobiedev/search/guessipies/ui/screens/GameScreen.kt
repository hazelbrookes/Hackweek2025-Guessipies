package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme

@Composable
fun GameScreen(
    onNavigateToHome: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Game Screen")
        Button({ onNavigateToHome() }) {
            Text("Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GuessipiesTheme {
        GameScreen {  }
    }
}