package com.mobiedev.search.guessipies.viewmodel

import androidx.lifecycle.ViewModel
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.models.Link
import com.mobiedev.search.guessipies.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameUiState(
    val currentRecipe: Recipe,
    val possibleAnswers: List<Recipe> = listOf(),
    val chain: Chain = stubChain
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        GameUiState(currentRecipe = Recipe("stub", listOf())
    ))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
}

private val stubChain = Chain(
    chain = listOf(
        Link(
            recipe1 = Recipe(
                title = "Recipe 1",
                ingredients = listOf("R1 & R2 matcher", "xyz")
            ),
            ingredient = "one thing",
            recipe2 = Recipe(
                title = "Recpipe 2",
                ingredients = listOf("R1 & R2 matcher", "R2 & R3 matcher", "abc")
            )
        ),
        Link(
            recipe1 = Recipe(
                title = "Recipe 2",
                ingredients = listOf("R1 & R2 matcher", "R2 & R3 matcher", "123")
            ),
            ingredient = "another",
            recipe2 = Recipe(
                title = "Recpipe 3",
                ingredients = listOf("R2 & R3 matcher", "cheese")
            )
        )
    ),
    score = 0
)