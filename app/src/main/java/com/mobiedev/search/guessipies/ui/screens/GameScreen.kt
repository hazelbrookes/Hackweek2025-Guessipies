package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.models.Link
import com.mobiedev.search.guessipies.models.Recipe
import com.mobiedev.search.guessipies.viewmodel.GameUiState
import com.mobiedev.search.guessipies.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel, onClickOpenRecipe: (String) -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        CurrentChain(
            chain = uiState.value.chain,
            uiState = uiState.value,
            onClickOpenRecipe = onClickOpenRecipe,
            modifier = Modifier
                .fillMaxWidth()
                .weight(.6f)
        )
        CurrentRecipe(uiState = uiState.value)
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
                            .clickable {
                                if (uiState.gameLive) {
                                    onClickGuess(recipe)
                                }
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
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(all = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Column(
            modifier = Modifier
                .height(100.dp)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            uiState.currentRecipe.ingredients.forEach { ingredient ->
                Text(
                    text = ingredient,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun Link(link: Link, onClickOpenRecipe: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.clickable {
                onClickOpenRecipe(link.recipe1.id)
            }
        ) {
            Text(
                text = link.recipe1.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
            )
            Image(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = "Open in browser",
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(24.dp)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Text(
            text = link.ingredient,
            modifier = Modifier
                .padding(all = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Row(
            modifier = Modifier.clickable {
                onClickOpenRecipe(link.recipe2.id)
            }
        ) {
            Text(
                text = link.recipe2.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
                    .fillMaxWidth()
            )
            Image(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = "Open in browser",
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(24.dp)
            )
        }
    }
}

@Composable
private fun CurrentChain(
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
            Link(
                link = link,
                onClickOpenRecipe = onClickOpenRecipe
            )
            if (index != chain.links.size - 1) {
                Image(
                    imageVector = Icons.Default.Link,
                    contentDescription = "",
                    modifier = Modifier
                        .rotate(90f)
                        .fillMaxWidth()
                        .size(24.dp)
                )
            }
        }
    }
}