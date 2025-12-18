package com.mobiedev.search.guessipies.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.ui.theme.GuessipiesTheme

@Composable
fun ScoresScreen(
    onClickOpenScores: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Scores",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = { onClickOpenScores() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        ) {
            Text(text = "Open in browser")
            Icon(
                imageVector = Icons.AutoMirrored.Default.OpenInNew,
                contentDescription = "",
                modifier = Modifier.padding(start = 8.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoresScreenPreview() {
    GuessipiesTheme {
        ScoresScreen {  }
    }
}