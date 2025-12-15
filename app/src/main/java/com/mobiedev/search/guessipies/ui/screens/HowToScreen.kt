package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme

@Composable
fun HowToScreen(
    onNavigateToHome: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ){
            Text(
                text = "How to play Guessipies!",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Text(text = "Players build a chain of recipes by matching ingredients. Each round, the player is given a starting recipe and must find a next recipe that shares at least one key ingredient. The goal is to create the longest possible chain before either guessing incorrectly or running out of valid moves.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Text(
                text = "Gameplay Loop",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Text(text = "1. Start:\n" +
                    "    - The game presents a random recipe (e.g., “Easy chocolate cake”).\n" +
                    "    - The four key ingredients are listed that are within the recipe.\n" +
                    "2. Chain Building:\n" +
                    "    - The player is shown four possible next recipes to choose from.\n" +
                    "    - To continue the chain, the next recipe chosen must share at least one key ingredient with the previous recipe (e.g., “chocolate”, “egg”, “flour”).\n" +
                    "    - Each valid move adds to the chain and reveals the new recipe’s details.\n" +
                    "3. End of Game:\n" +
                    "    - The game ends when a player guesses incorrectly or no more valid recipes can be chained from the current one.\n" +
                    "    - The player’s score is the length of the chain.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Text(
                text = "Example Round",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Text(text = "- Start: “Easy chocolate cake” (main ingredients: chocolate, flour, egg)\n" +
                    "- Player picks: “Breakfast Casserole” (shares egg)\n" +
                    "- Next: “Bangers & Mash” (shares sausage)\n" +
                    "- Next: “Homemade Chips” (shares potato)\n" +
                    "- ...and so on, and so forth.",
                modifier = Modifier.padding(16.dp)
            )
        }

        Button({ onNavigateToHome() }) {
            Text("Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HowToScreenPreview() {
    GuessipiesTheme {
        HowToScreen {  }
    }
}