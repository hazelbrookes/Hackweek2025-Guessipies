package com.mobiedev.search.guessipies

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.text.contains

fun getRandomLetter(): Char {
    val alphabet = ('a'..'z').filter { it != 'x' }
    return alphabet.random()
}

suspend fun fetchRecipeSlugsAndIds(letter: Char): List<String> = withContext(Dispatchers.IO) {
    val url = "https://www.bbc.co.uk/food/recipes/a-z/${letter}/1"
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            Log.e("FETCH", "HTTP error: ${response.code}")
            throw Exception("Unexpected code $response")
        }
        val html = response.body?.string() ?: return@withContext emptyList()
        val doc = Jsoup.parse(html)
        val links = doc.select("a[href^=/food/recipes/]")

        Log.d("FETCH", "Found ${links.size} recipe links for letter $letter")
        return@withContext links.mapNotNull { link ->
            val href = link.attr("href")
            val regex = Regex("/food/recipes/([a-zA-Z0-9_]+)")
            regex.find(href)?.groupValues?.get(1)
        }
    }
}

suspend fun fetchRecipeTitleAndIngredients(slug: String): Pair<String?, List<String>> = withContext(Dispatchers.IO) {
    val url = "https://www.bbc.co.uk/food/recipes/$slug"
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    try {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("FETCH", "HTTP error: ${response.code}")
                return@withContext Pair(null, emptyList())
            }
            val html = response.body?.string() ?: return@withContext Pair(null, emptyList())
            val doc = Jsoup.parse(html)
            val title = doc.selectFirst("h1[class*='Title']")?.text()
                ?: doc.selectFirst("h1")?.text()
            Log.d("FETCH", "Fetched HTML for $slug, parsing ingredients")

            val ingredientList = doc.select("li")
            Log.d("FETCH", "Found li count: ${ingredientList.size}")

            val measurementUnits = setOf(
                "g", "gram", "grams", "kg", "kilogram", "kilograms",
                "oz", "ounce", "ounces", "lb", "pound", "pounds",
                "ml", "l", "litre", "litres", "liter", "liters",
                "tbsp", "tablespoon", "tablespoons", "tsp", "teaspoon", "teaspoons",
                "cup", "cups", "pint", "pints", "quart", "quarts", "gallon", "gallons",
                "cm", "mm", "inch", "inches", "clove", "cloves", "slice", "slices"
            )

            val regex = Regex("^([\\d]+([.,]\\d+)?|[¼½¾⅐⅑⅒⅓⅔⅕⅖⅗⅘⅙⅚⅛⅜⅝⅞])\\s*([a-zA-Z]+)?\\s*")
            val timeWords = setOf("minute", "second")

            val ingredients = ingredientList.mapNotNull { li ->
                var text = li.text().trim()
                if (regex.containsMatchIn(text)) {
                    text = text.substringBefore(",").trim()
                    if (timeWords.any { text.contains(it, ignoreCase = true) }) return@mapNotNull null
                    var name = text.replaceFirst(regex, "").trimStart()
                    // Remove all words containing digits or measurement units
                    name = name.split("\\s+".toRegex())
                        .filter { word ->
                            !word.any { ch -> ch.isDigit() } &&
                                    !measurementUnits.contains(word.lowercase())
                        }
                        .joinToString(" ")
                        .replace("\\s+".toRegex(), " ")
                        .trimStart()
                    // If starts with '/', remove that substring and following space
                    if (name.startsWith("/")) {
                        name = name.substringAfter(" ").trimStart()
                    }
                    if (name.isNotEmpty()) name else null
                } else null
            }


            Log.d("FETCH", "Found ${ingredientList.size} ingredient list items")
            Log.d("FETCH", "Fetched title: $title, ingredients count: ${ingredients.size}")
            return@withContext Pair(title, ingredients)
        }
    } catch (e: Exception) {
        Log.e("FETCH", "Error fetching title and ingredients: ${e.message}", e)
        return@withContext Pair(null, emptyList())
    }
}

suspend fun randomRecipeInfo(): Pair<String?, List<String>> = withContext(Dispatchers.IO) {
    Log.d("RECIPE_INFO", "Starting random recipe info fetch")
    val letter = getRandomLetter()
    Log.d("RECIPE_INFO", "Selected random letter: $letter")
    try {
        val slugs = fetchRecipeSlugsAndIds(letter)
        if (slugs.isEmpty()) {
            Log.e("RECIPE_INFO", "No recipes found for letter $letter")
            return@withContext Pair(null, emptyList())
        }
        // Only keep slugs that end with _number
        val validSlugs = slugs.filter { it.matches(Regex(".*_\\d+$")) }
        if (validSlugs.isEmpty()) {
            Log.e("RECIPE_INFO", "No valid recipe slugs found for letter $letter")
            return@withContext Pair(null, emptyList())
        }
        Log.d("RECIPE_INFO", "Found ${validSlugs.size} valid recipes for letter $letter")
        val randomSlug = validSlugs.random()
        Log.d("RECIPE_INFO", "Selected random slug: $randomSlug")
        val titleAndIngredients = fetchRecipeTitleAndIngredients(randomSlug)
        Log.d("RECIPE_INFO", "Fetched recipe: ${titleAndIngredients.first}")
        Log.d("RECIPE_INFO", "Fetched ingredients: ${titleAndIngredients.second}")
        val mainIngredients = titleAndIngredients.second.map { extractMainIngredient(it) }
        Log.d("RECIPE_INFO", "Extracted main ingredients: $mainIngredients")
        val titleAndMainIngredients = Pair(titleAndIngredients.first, mainIngredients)
        return@withContext titleAndMainIngredients
    } catch (e: Exception) {
        Log.e("RECIPE_INFO", "Error in randomRecipeInfo: ${e.message}", e)
        return@withContext Pair(null, emptyList())
    }
}

val descriptors = setOf(
    // Preparation methods
    "dried", "fresh", "chopped", "sliced", "grated", "crushed", "ground", "minced", "optional",
    "firm", "raw", "large", "small", "medium", "fillet", "fillets", "stick", "sticks", "sticks of",
    "flaked", "free-range", "boneless", "skinless", "cooked", "peeled", "seeded", "halved", "quartered",
    "pitted", "zested", "shredded", "rindless", "trimmed", "beaten", "softened", "unsalted", "salted",
    "smoked", "roasted", "toasted", "blanched", "boiled", "steamed", "baked", "fried", "poached",
    "pickled", "marinated", "candied", "caramelized", "deveined", "deshelled", "shelled", "skinned",
    "split", "whole", "crumbled", "cubed", "diced", "smashed", "mashed", "pureed", "rinsed", "washed",
    "drained", "soaked", "sifted", "sieved", "beaten", "whipped", "stiff", "soft", "hard", "runny",
    "thick", "thin", "lean", "fatty", "fat-free", "low-fat", "reduced-fat", "full-fat", "double", "single",
    "light", "dark", "sweet", "sour", "bitter", "spicy", "hot", "cold", "warm", "room-temperature",
    "organic", "non-organic", "pasteurized", "unpasteurized", "evaporated", "condensed", "powdered",
    "granulated", "caster", "icing", "brown", "white", "yellow", "red", "green", "black", "purple",
    "orange", "pink", "blue", "golden", "silver", "plain", "self-raising", "self raising", "wholemeal",
    "whole-wheat", "whole wheat", "multigrain", "gluten-free", "vegan", "vegetarian", "kosher", "halal",
    "cored", "de-seeded", "seedless", "pitted", "stoned", "with skin", "without skin", "with seeds",
    "without seeds", "with bone", "without bone", "boned", "deboned", "trimmed", "ends removed",
    "tops removed", "tails removed", "hearts", "hearts of", "tips", "tips of", "segments", "segments of",
    "pieces", "pieces of", "strips", "strips of", "rings", "rings of", "wedges", "wedges of", "quarters",
    "quarters of", "halves", "halves of", "chunks", "chunks of", "balls", "balls of", "logs", "logs of",
    "rolls", "rolls of", "sheets", "sheets of", "leaves", "leaves of", "sprigs", "sprigs of", "bunch",
    "bunch of", "bunches", "bunches of", "handful", "handful of", "handfuls", "handfuls of", "clove",
    "cloves", "cloves of", "bulb", "bulbs", "bulbs of", "head", "heads", "heads of", "stalk", "stalks",
    "stalks of", "rib", "ribs", "ribs of", "ear", "ears", "ears of", "pod", "pods", "pods of", "can",
    "cans", "cans of", "tin", "tins", "tins of", "jar", "jars", "jars of", "packet", "packets", "packets of",
    "bag", "bags", "bags of", "carton", "cartons", "cartons of", "bottle", "bottles", "bottles of",
    "box", "boxes", "boxes of", "tube", "tubes", "tubes of", "sachet", "sachets", "sachets of",
    "envelope", "envelopes", "envelopes of", "roll", "rolls", "rolls of", "sheet", "sheets", "sheets of",
    "slice", "slices", "slices of", "piece", "pieces", "pieces of", "cube", "cubes", "cubes of", "chunk",
    "chunks", "chunks of", "strip", "strips", "strips of", "ring", "rings", "rings of", "wedge", "wedges",
    "wedges of", "quarter", "quarters", "quarters of", "half", "halves", "halves of", "ball", "balls",
    "balls of", "log", "logs", "logs of", "roll", "rolls", "rolls of", "sheet", "sheets", "sheets of",
    "leaf", "leaves", "leaves of", "sprig", "sprigs", "sprigs of", "bunch", "bunches", "bunches of",
    "handful", "handfuls", "handfuls of", "clove", "cloves", "cloves of", "bulb", "bulbs", "bulbs of",
    "head", "heads", "heads of", "stalk", "stalks", "stalks of", "rib", "ribs", "ribs of", "ear", "ears",
    "ears of", "pod", "pods", "pods of", "can", "cans", "cans of", "tin", "tins", "tins of", "jar", "jars",
    "jars of", "packet", "packets", "packets of", "bag", "bags", "bags of", "carton", "cartons",
    "cartons of", "bottle", "bottles", "bottles of", "box", "boxes", "boxes of", "tube", "tubes",
    "tubes of", "sachet", "sachets", "sachets of", "envelope", "envelopes", "envelopes of"
)

fun extractMainIngredient(ingredient: String): String {
    // Remove all parentheses and their contents (global, non-greedy)
    var result = ingredient.replace(
        "\\(.*?\\)".toRegex(setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL)),
        ""
    ).trim()
    // Remove " or ..." and everything after, including the space before "or"
    result = result.replace("\\s+or\\b.*".toRegex(RegexOption.IGNORE_CASE), "").trim()
    // Remove leading multi-word descriptors
    for (desc in descriptors.sortedByDescending { it.length }) {
        if (result.lowercase().startsWith(desc + " ")) {
            result = result.substring(desc.length).trimStart()
        }
    }
    // Remove leading single-word descriptors
    val words = result.split("\\s+".toRegex()).toMutableList()
    while (words.isNotEmpty() && descriptors.contains(words[0].lowercase())) {
        words.removeAt(0)
    }
    return words.joinToString(" ").trim()
}