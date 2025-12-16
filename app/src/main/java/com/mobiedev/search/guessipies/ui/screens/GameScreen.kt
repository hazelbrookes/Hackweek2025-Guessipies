package com.mobiedev.search.guessipies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import com.mobiedev.search.guessipies.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CurrentChain(
            chain = uiState.value.chain,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text("THIS IS HAZEL'S STUFF BELOW HERE")
    }
}

@Composable
private fun CurrentChain(chain: Chain, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(chain.chain) { index, link ->
            Text(link.recipe1.title)
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GuessipiesTheme {
        GameScreen(viewModel = GameViewModel())
    }
}