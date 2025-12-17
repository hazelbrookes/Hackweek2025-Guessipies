package com.mobiedev.search.guessipies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mobiedev.search.guessipies.viewmodel.GameUiState

@Composable
fun CurrentRecipeCard(uiState: GameUiState, onClickOpenRecipe: (String) -> Unit) {
    Card(
        modifier = Modifier
            .clearAndSetSemantics{
                contentDescription = "Current Recipe: " + uiState.currentRecipe.title + "." + "Ingredients: " + uiState.currentRecipe.ingredients.joinToString(",")
            }
            .clickable {
                onClickOpenRecipe(uiState.currentRecipe.id)
            }
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        RecipeImage(uiState.currentRecipe.imageUrl ?: "")
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
private fun RecipeImage(url: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = url)
                .crossfade(true)
                .build()
        )
        val state by painter.state.collectAsState()

        when (state) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painter,
                    contentDescription = ""
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxWidth()
                )
            }
        }
    }
}
