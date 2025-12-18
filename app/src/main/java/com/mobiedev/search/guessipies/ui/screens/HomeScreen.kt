package com.mobiedev.search.guessipies.ui.screens

import android.R.attr.label
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobiedev.search.guessipies.GuessipiesAppNavigation
import com.mobiedev.search.guessipies.network.UsernameDataStore
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme

@Composable
fun HomeScreen(
    onNavigateToGame: () -> Unit
){

    val context = LocalContext.current
    val usernameDataStore = remember(context) { UsernameDataStore(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.05f))
        Text(
            text = "Guessipies",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 44.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 50.dp),
        )
        Username(usernameDataStore)
        Spacer(modifier = Modifier.weight(0.25f))
        Button(
            onClick = { onNavigateToGame() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .size(250.dp)
                .clearAndSetSemantics{
                contentDescription = "Play Game"
                role = Role.Button
            }
        ) {
            Text(text = "Play!",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 50.sp,)
        }
        Spacer(modifier = Modifier.weight(0.25f))
        Button(
            {  },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clearAndSetSemantics{
                contentDescription = "Give Feedback"
                role = Role.Button
            }
        ) {
            Text("Feedback")
        }
    }
}

@Composable
fun Username(
    usernameDataStore: UsernameDataStore
){
    val username by usernameDataStore.usernameFlow().collectAsState(null)

    if(username != null){
        Text(username)
    } else {
        TextField(
            value = "",
            onValueChange = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    GuessipiesTheme {
        HomeScreen(
            onNavigateToGame = {}
        )
    }
}