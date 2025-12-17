package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.ui.components.CurrentRecipeCard
import com.mobiedev.search.guessipies.ui.components.LinkCard
import com.mobiedev.search.guessipies.ui.components.PossibleAnswersGrid
import com.mobiedev.search.guessipies.ui.components.ScoreCard
import com.mobiedev.search.guessipies.viewmodel.GameUiState
import com.mobiedev.search.guessipies.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onClickOpenRecipe: (String) -> Unit,
    onNavigateToResults: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.value.gameLive){
        if(!uiState.value.gameLive){
            onNavigateToResults()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        CurrentChain(
//            chain = uiState.value.chain,
//            uiState = uiState.value,
//            onClickOpenRecipe = onClickOpenRecipe,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(.6f)
//        )
        CurrentRecipeCard(uiState = uiState.value, onClickOpenRecipe)
        PossibleAnswersGrid(
            uiState = uiState.value,
            onClickGuess = { viewModel.onClickGuess(it) },
            modifier = Modifier
                .weight(1f)
        )
        ScoreCard(
            uiState.value,
            modifier = Modifier
        )
    }
}

@Composable
fun CurrentChain(
    uiState: GameUiState,
    chain: Chain,
    onClickOpenRecipe: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        if (!uiState.gameLive) {
            item {
                Image(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(24.dp)
                )
            }
        }
        itemsIndexed(chain.links.reversed()) { index, link ->
            LinkCard(
                link = link,
                onClickOpenRecipe = onClickOpenRecipe
            )
            if (index != chain.links.size - 1) {
                Image(
                    imageVector = Icons.Default.Link,
                    contentDescription = "",
                    modifier = Modifier
                        .rotate(90f)
                        .clearAndSetSemantics { }
                        .fillMaxWidth()
                        .size(24.dp)
                )
            }
        }
    }
}