package com.mobiedev.search.guessipies.ui.screens

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.text.Layout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import com.mobiedev.search.guessipies.viewmodel.GameUiState
import com.mobiedev.search.guessipies.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CurrentRecipe(uiState.value)
        PossibleAnswersGrid(uiState.value)
        ScoreCard(uiState.value)
    }
}

@Composable
fun ScoreCard(
    uiState: GameUiState
){
    OutlinedCard(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Start)
                .height(100.dp)
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Default.Settings,
                "Cog Icon",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(20.dp)
            )
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically),
                text = uiState.chain.score.toString(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun PossibleAnswersGrid(
    uiState: GameUiState
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding( 20.dp)
    ) {
        uiState.possibleAnswers.forEach { recipe ->
            item {
                Card(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(5.dp)
                        .clickable{ }
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
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

@Composable
fun CurrentRecipe(
    uiState: GameUiState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Text(
            text = uiState.currentRecipe.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        HorizontalDivider()
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            uiState.currentRecipe.ingredients.forEach { ingr ->
                Text(
                    text = ingr,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GuessipiesTheme {
        GameScreen(
            viewModel = GameViewModel()
        )
    }
}