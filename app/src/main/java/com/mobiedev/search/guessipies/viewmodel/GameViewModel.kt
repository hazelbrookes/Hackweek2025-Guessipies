package com.mobiedev.search.guessipies.viewmodel

import android.system.Os.link
import androidx.lifecycle.ViewModel
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.models.Link
import com.mobiedev.search.guessipies.models.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val currentRecipe: Recipe,
    val possibleAnswers: List<Recipe> = listOf(
        Recipe("Chocolate Cake", listOf("egg", "flour", "milk")),
        Recipe("Sponge Cake", listOf("egg", "sponge", "jam")),
        Recipe("Cheesecake", listOf("cheese", "flour", "cake")),
        Recipe("Lemon Drizzle", listOf("lemon", "egg", "milk"))
    ),
    val gameLive: Boolean = true,
    val chain: Chain = Chain(links = listOf(), score = 0)
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        GameUiState(currentRecipe = Recipe("Current Cake", listOf("egg", "flour", "milk"))
    ))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onClickGuess(recipeGuessed: Recipe) {
        uiState.value.currentRecipe.linksToOtherRecipe(otherRecipe = recipeGuessed)?.let { link ->
            updateLinks(
                recipeGuessed = recipeGuessed,
                link = link
            )
            // fetch new items
        } ?: run {
            endGame()
        }
    }

    private fun updateLinks(recipeGuessed: Recipe, link: Link) {
        val newLinks = buildList {
            addAll(uiState.value.chain.links)
            add(link)
        }
        _uiState.update {
            uiState.value.copy(
                currentRecipe = recipeGuessed, // TODO: remove
                chain = uiState.value.chain.copy(
                    links = newLinks,
                    score = newLinks.size
                )
            )
        }
    }

    private fun endGame() {
        _uiState.update {
            uiState.value.copy(gameLive = false)
        }
    }
}
