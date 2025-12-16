package com.mobiedev.search.guessipies.viewmodel

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
        } ?: run {
            _uiState.update {
                uiState.value.copy(gameLive = false)
            }
        }
    }
}

private val stubChain = Chain(
    links = listOf(
        Link(
            recipe1 = Recipe(
                title = "Recipe 1",
                ingredients = listOf("R1 & R2 matcher", "xyz")
            ),
            ingredient = "R1 & R2 matcher",
            recipe2 = Recipe(
                title = "Recipe 2",
                ingredients = listOf("R1 & R2 matcher", "R2 & R3 matcher", "abc")
            )
        ),
        Link(
            recipe1 = Recipe(
                title = "Recipe 2",
                ingredients = listOf("R1 & R2 matcher", "R2 & R3 matcher", "123")
            ),
            ingredient = "R2 & R3 matcher",
            recipe2 = Recipe(
                title = "Recipe 3",
                ingredients = listOf("R2 & R3 matcher", "R3 & R4 matcher")
            )
        ),
        Link(
            recipe1 = Recipe(
                title = "Recipe 3",
                ingredients = listOf("R2 & R3 matcher", "R3 & R4 matcher")
            ),
            ingredient = "R3 & R4 matcher",
            recipe2 = Recipe(
                title = "Recipe 4",
                ingredients = listOf("R3 & R4 matcher", "R4 & R5 matcher")
            )
        ),
        Link(
            recipe1 = Recipe(
                title = "Recipe 4",
                ingredients = listOf("R4 & R5 matcher", "cheese")
            ),
            ingredient = "R4 & R5 matcher",
            recipe2 = Recipe(
                title = "Recipe 5",
                ingredients = listOf("R4 & R5 matcher", "beans")
            )
        )
    ),
    score = 0
)