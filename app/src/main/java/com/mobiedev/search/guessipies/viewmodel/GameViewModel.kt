package com.mobiedev.search.guessipies.viewmodel

import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiedev.search.guessipies.mappers.toRecipe
import com.mobiedev.search.guessipies.models.Chain
import com.mobiedev.search.guessipies.models.Link
import com.mobiedev.search.guessipies.models.Recipe
import com.mobiedev.search.guessipies.network.RecipesFetcher
import com.mobiedev.search.guessipies.network.ScorePoster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameUiState(
    val currentRecipe: Recipe,
    val possibleAnswers: List<Recipe> = listOf(
        Recipe(id = "1", title = "Chocolate Cake", ingredients = listOf("egg", "flour", "milk")),
        Recipe(id = "2", title = "Sponge Cake", ingredients = listOf("egg", "sponge", "jam")),
        Recipe(id = "3", title = "Cheesecake", ingredients = listOf("cheese", "flour", "cake")),
        Recipe(id = "4", title = "Lemon Drizzle", ingredients = listOf("lemon", "egg", "milk"))
    ),
    val gameLive: Boolean = true,
    val chain: Chain = Chain(links = listOf(), score = 0),
    val isLoading: Boolean = false
)

class GameViewModel(
    private val recipesFetcher: RecipesFetcher,
    private val ScorePoster: ScorePoster
) : ViewModel() {

    init {
        getRecipes()
    }

    private val _uiState = MutableStateFlow(
        value = GameUiState(
            currentRecipe = Recipe(
                id = "5",
                title = "Current Cake",
                ingredients = listOf("egg", "flour", "milk")
            ),
            isLoading = false
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onClickGuess(recipeGuessed: Recipe) {
        uiState.value.currentRecipe.linksToOtherRecipe(otherRecipe = recipeGuessed)?.let { link ->
            updateLinks(
                recipeGuessed = recipeGuessed,
                link = link
            )
            _uiState.update {
                uiState.value.copy(
                    isLoading = true
                )
            }
            getRecipes(recipeGuessed)
        } ?: run {
            endGame()
        }
    }

    fun getRecipes(recipe: Recipe? = null) {
        viewModelScope.launch {
            val data = recipesFetcher.getRecipes(recipe)
            data?.let {
                _uiState.update {
                    uiState.value.copy(
                        currentRecipe = data.currentRecipe.toRecipe(),
                        possibleAnswers = data.recipes.map { it.toRecipe() }.shuffled(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun resetGameState(){
        _uiState.update {
            uiState.value.copy(
                gameLive = true,
                chain = Chain(links = listOf(), score = 0)
            )
        }
        getRecipes()
    }

    private fun updateLinks(recipeGuessed: Recipe, link: Link) {
        val newLinks = buildList {
            addAll(uiState.value.chain.links)
            add(link)
        }
        _uiState.update {
            uiState.value.copy(
                currentRecipe = recipeGuessed,
                chain = uiState.value.chain.copy(
                    links = newLinks,
                    score = newLinks.size
                )
            )
        }
    }

    private fun endGame() {
        viewModelScope.launch {
            try {
                ScorePoster.submitScore(
                    score = uiState.value.chain.score,
                    username = "KevinBacon",
                    game = "guessipies"
                )
            } catch (e: Exception) {
                Log.d("HELP", "endGame: ${e.message}" )
            }
        }
        _uiState.update {
            uiState.value.copy(gameLive = false)
        }
    }
}
