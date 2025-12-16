package com.mobiedev.search.guessipies.network

import com.mobiedev.search.guessipies.models.GuessipiesData
import com.mobiedev.search.guessipies.models.GuessipiesPayload
import com.mobiedev.search.guessipies.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class RecipesFetcher(
    private val okHttpClient: OkHttpClient
) {

    suspend fun getRecipes(recipe: Recipe?): GuessipiesData? = withContext(Dispatchers.IO) {
        val url = if(recipe != null){
            "https://bruce.belfrage.test.api.bbc.co.uk/fd/preview/spike-app-food-data?current=${recipe}"
        } else {
            "https://bruce.belfrage.test.api.bbc.co.uk/fd/preview/spike-app-food-data"
        }
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val body = response.body?.string()
                val payload = body?.let { Json.decodeFromString<GuessipiesPayload>(body) }
                payload?.data
            } else null
        }
    }
}