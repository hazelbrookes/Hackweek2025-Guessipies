package com.mobiedev.search.guessipies.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.models.Recipe
import com.mobiedev.search.guessipies.viewmodel.GameUiState

@Composable
fun PossibleAnswersGrid(
    uiState: GameUiState,
    onClickGuess: (Recipe) -> Unit,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        if (uiState.isLoading) {
            items(4) {
                Card(
                    modifier = Modifier
                        .clearAndSetSemantics {
                            contentDescription = "Loading Answers"
                        }
                        .height(130.dp)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        } else {
            uiState.possibleAnswers.forEach { recipe ->
                item {
                    Card(
                        modifier = Modifier
                            .height(130.dp)
                            .clickable(
                                onClickLabel = "make connection guess",
                                onClick = {
                                    if (uiState.gameLive) {
                                        onClickGuess(recipe)
                                    }
                                }
                            )
                            .clearAndSetSemantics{
                                contentDescription = "Possible Answer: " + recipe.title
                            }
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = recipe.title,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}