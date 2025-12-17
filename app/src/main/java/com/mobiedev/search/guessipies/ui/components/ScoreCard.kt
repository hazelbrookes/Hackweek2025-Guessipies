package com.mobiedev.search.guessipies.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.viewmodel.GameUiState

@Composable
fun ScoreCard(
    uiState: GameUiState,
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier
            .clearAndSetSemantics{
                contentDescription = "Game score: " + uiState.chain.score.toString()
            }
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Start)
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Cog Icon",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 20.dp)
            )
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically),
                text = uiState.chain.score.toString(),
                textAlign = TextAlign.Start
            )
        }
    }
}