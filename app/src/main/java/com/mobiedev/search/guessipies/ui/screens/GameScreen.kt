package com.mobiedev.search.guessipies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import com.mobiedev.search.guessipies.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onNavigateToHome: () -> Unit
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Game Screen")
        Button({ onNavigateToHome() }) {
            Text("Home")
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GuessipiesTheme {
        GameScreen(
            viewModel = GameViewModel(),
            {}
        )
    }
}