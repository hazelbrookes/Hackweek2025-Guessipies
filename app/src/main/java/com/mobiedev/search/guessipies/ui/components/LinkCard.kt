package com.mobiedev.search.guessipies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobiedev.search.guessipies.models.Link

@Composable
fun LinkCard(link: Link, onClickOpenRecipe: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    onClickLabel = "open recipe in browser",
                    onClick = { onClickOpenRecipe(link.recipe1.id) }
                )
                .clearAndSetSemantics {
                    contentDescription = "Chained Recipe: " + link.recipe1.title
                }
        ) {
            Text(
                text = link.recipe1.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Text(
            text = link.ingredient,
            modifier = Modifier
                .clearAndSetSemantics{
                    contentDescription = "Linked Ingredient: " + link.ingredient
                }
                .padding(all = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp))
        Row(
            modifier = Modifier
                .clickable(
                    onClickLabel = "open recipe in browser",
                    onClick = { onClickOpenRecipe(link.recipe2.id) }
                )
                .clearAndSetSemantics{
                    contentDescription = "Chained Recipe: " + link.recipe2.title
                }
        ) {
            Text(
                text = link.recipe2.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .weight(1f)
                    .fillMaxWidth()
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = null,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}