package com.mobiedev.search.guessipies.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuessipiesPayload(
    val data: GuessipiesData
)

@Serializable
data class GuessipiesData(
    val currentRecipe: RemoteRecipe,
    val recipes: List<RemoteRecipe>
)

@Serializable
data class RemoteRecipe(
    val id: String,
    val title: String,
    val instructions: Instructions,
    val media: Media? = null
)

@Serializable
data class Media(
    val image: Image? = null
)

@Serializable
data class Image(
    @SerialName("template") val url: String?
)

@Serializable
data class Instructions(
    @SerialName("ingredients") val stages: List<IngredientStage>
)

@Serializable
data class IngredientStage(
    @SerialName("ingredients") val sections: List<Section>
)

@Serializable
data class Section(
    @SerialName("ingredients") val ingredients: List<Ingredient?>
)

@Serializable
data class Ingredient(
    @SerialName("id") val name: String?
)