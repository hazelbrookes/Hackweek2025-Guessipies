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
    val chain: Chain = Chain(listOf(), 0)
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        GameUiState(currentRecipe = Recipe("Chocolate Cake", listOf("egg", "flour", "milk"))
    ))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()



}