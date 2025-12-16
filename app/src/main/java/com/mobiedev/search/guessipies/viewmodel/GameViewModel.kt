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
        Recipe(id = "1", title = "Chocolate Cake", ingredients = listOf("egg", "flour", "milk")),
        Recipe(id = "2", title = "Sponge Cake", ingredients = listOf("egg", "sponge", "jam")),
        Recipe(id = "3", title = "Cheesecake", ingredients = listOf("cheese", "flour", "cake")),
        Recipe(id = "4", title = "Lemon Drizzle", ingredients = listOf("lemon", "egg", "milk"))
    ),
    val gameLive: Boolean = true,
    val chain: Chain = Chain(links = listOf(), score = 0)
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        value = GameUiState(
            currentRecipe = Recipe(
                id = "5",
                title = "Current Cake",
                ingredients = listOf("egg", "flour", "milk")
            )
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onClickGuess(recipeGuessed: Recipe) {
        uiState.value.currentRecipe.linksToOtherRecipe(otherRecipe = recipeGuessed)?.let { link ->
            updateLinks(
                recipeGuessed = recipeGuessed,
                link = link
            )
            // TODO: fetch new items
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
