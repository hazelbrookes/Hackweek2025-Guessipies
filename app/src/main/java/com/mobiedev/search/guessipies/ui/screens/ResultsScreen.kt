package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiedev.search.guessipies.ui.components.LinkCard
import com.mobiedev.search.guessipies.ui.components.ScoreCard
import com.mobiedev.search.guessipies.viewmodel.GameViewModel

@Composable
fun ResultsScreen(
    viewModel: GameViewModel,
    onClickOpenRecipe: (String) -> Unit,
    onNavigateToGame: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Show full game chain
        CurrentChain(
            chain = uiState.value.chain,
            uiState = uiState.value,
            onClickOpenRecipe = onClickOpenRecipe,
            modifier = Modifier
                .fillMaxWidth()
                .weight(.6f)
        )
        //Show score
        ScoreCard(uiState.value)
        //Show reset game button
        Button(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                viewModel.resetGameState()
                onNavigateToGame()
            }
        ){
            Text("Start new game")
        }
    }
}