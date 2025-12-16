package com.mobiedev.search.guessipies.models

data class Recipe (
    val title: String,
    val ingredients : List<String>
)

data class Chain (
    val chain: List<Link>,
    val score: Int
)

data class Link (
    val recipe1: Recipe,
    val ingredient: String,
    val recipe2: Recipe
)