package com.mobiedev.search.guessipies.ui.components

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.viewmodel.GameUiState

@Composable
fun CurrentRecipeCard(uiState: GameUiState) {
    Card(
        modifier = Modifier
            .clearAndSetSemantics{
                contentDescription = "Current Recipe: " + uiState.currentRecipe.title + "." + "Ingredients: " + uiState.currentRecipe.ingredients.joinToString(",")
            }
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
