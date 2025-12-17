package com.mobiedev.search.guessipies.mappers

import com.mobiedev.search.guessipies.models.Recipe
import com.mobiedev.search.guessipies.models.RemoteRecipe


fun RemoteRecipe.toRecipe(): Recipe = Recipe(
    id = this.id,
    title = this.title,
    imageUrl = this.media?.image?.url?.replace("{width}", "608"),
    ingredients = this.instructions.stages.flatMap { stage ->
        stage.sections.flatMap { section ->
            section.ingredients.mapNotNull { ingredient ->
                ingredient?.name?.replace("_", " ")?.capitalize()
            }.distinct()
        }.distinct()
    }.distinct().shuffled()
)
