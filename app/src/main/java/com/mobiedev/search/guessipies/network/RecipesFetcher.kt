package com.mobiedev.search.guessipies.network

import android.util.Log
import com.mobiedev.search.guessipies.models.GuessipiesData
import com.mobiedev.search.guessipies.models.GuessipiesPayload
import com.mobiedev.search.guessipies.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class RecipesFetcher(
    private val okHttpClient: OkHttpClient
) {

    suspend fun getRecipes(recipe: Recipe?): GuessipiesData? = withContext(Dispatchers.IO) {
        val url = if(recipe != null){
            "https://bruce.belfrage.test.api.bbc.co.uk/fd/preview/spike-app-food-data?current=${recipe.id}"
        } else {
            "https://bruce.belfrage.test.api.bbc.co.uk/fd/preview/spike-app-food-data"
        }
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                val json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                val body = response.body?.string()
                val payload = body?.let { json.decodeFromString<GuessipiesPayload>(body) }
                payload?.data
            } else null
        }
    }
}