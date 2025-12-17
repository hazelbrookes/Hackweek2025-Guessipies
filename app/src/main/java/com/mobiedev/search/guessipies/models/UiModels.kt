package com.mobiedev.search.guessipies.models

data class Recipe (
    val id: String,
    val title: String,
    val ingredients: List<String>,
    val imageUrl: String? = null
) {
    fun linksToOtherRecipe(otherRecipe: Recipe): Link? {
        if (this == otherRecipe) return null
        this.ingredients.forEach { ingredient ->
            if (otherRecipe.ingredients.contains(ingredient)) {
                return Link(
                    recipe1 = this,
                    recipe2 = otherRecipe,
                    ingredient = ingredient
                )
            }
        }
        return null
    }
}

data class Chain (
    val links: List<Link>,
    val score: Int
)

data class Link (
    val recipe1: Recipe,
    val ingredient: String,
    val recipe2: Recipe
)