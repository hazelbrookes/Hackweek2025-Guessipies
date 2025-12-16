package com.mobiedev.search.guessipies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
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
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.models.Link
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme
import com.mobiedev.search.guessipies.viewmodel.GameUiState
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
                .weight(.7f)
        )
        CurrentRecipe(
            uiState = uiState.value
        )
        PossibleAnswersGrid(
            uiState = uiState.value,
            modifier = Modifier
                .weight(1f)
        )
        ScoreCard(
            uiState.value,
            modifier = Modifier
                .weight(.3f)
        )
    }
}

@Composable
fun ScoreCard(
    uiState: GameUiState,
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Start)
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Cog Icon",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 20.dp)
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
    uiState: GameUiState,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(horizontal = 20.dp)
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
fun CurrentRecipe(uiState: GameUiState) {
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

@Composable
fun Link(link: Link) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 10.dp)
    ) {
        Text(
            text = link.recipe1.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Text(
            text = link.ingredient,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Text(
            text = link.recipe2.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun CurrentChain(chain: Chain, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        itemsIndexed(chain.chain.reversed()) { index, link ->
            Link(link)
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