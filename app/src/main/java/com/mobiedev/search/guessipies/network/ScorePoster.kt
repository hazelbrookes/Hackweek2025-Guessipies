package com.mobiedev.search.guessipies.network
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScorePoster(
    private val okHttpClient: OkHttpClient
) {
    // Stub for getting a cookie (implement as needed for your environment)
    fun getCookie(name: String): String? {
        // TODO: Implement cookie retrieval for your environment
        return null
    }

    // Decodes a base64 URI-encoded string using Android's Base64
    fun decodeBase64URIEncodedString(encoded: String): String {
        val decodedBytes = Base64.decode(encoded, Base64.URL_SAFE or Base64.NO_WRAP)
        return String(decodedBytes, Charsets.UTF_8)
    }

    @Throws(Exception::class)
    suspend fun submitScore(
        score: Int,
        username: String? = null,
        game: String,
        date: String? = null
    ): okhttp3.Response = withContext(Dispatchers.IO) {
        // Retrieve username from cookie if not provided
        var finalUsername = username
        if (finalUsername.isNullOrBlank()) {
            val signInCookie = getCookie("ckns_id")
            val signInDetails = signInCookie?.let {
                val decoded = decodeBase64URIEncodedString(it)
                JSONObject(decoded)
            }
            finalUsername = signInDetails?.optString("dn")
        }
        if (finalUsername.isNullOrBlank()) {
            throw Exception("No username found. Please log in.")
        }

        // Validate username and game (alphanumeric only)
        val alphanumeric = Regex("^[a-zA-Z0-9]+$")
        if (!alphanumeric.matches(finalUsername)) {
            throw Exception("Username must be alphanumeric")
        }
        if (!alphanumeric.matches(game)) {
            throw Exception("Game name must be alphanumeric")
        }

        // Validate or set date
        val finalDate = date ?: SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val dateRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!dateRegex.matches(finalDate)) {
            throw Exception("Date must be in YYYY-MM-DD format")
        }

        val bodyJson = JSONObject()
            .put("username", finalUsername)
            .put("score", score)
            .put("game", game)
            .put("date", finalDate)
            .toString()

        val request = Request.Builder()
            .url("https://faa9gwlric.execute-api.eu-west-1.amazonaws.com/prod/score")
            .post(bodyJson.toRequestBody("application/json".toMediaType()))
            .addHeader("Content-Type", "application/json")
            .build()

        okHttpClient.newCall(request).execute()
    }
}