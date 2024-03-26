package com.example.helpersapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryRadioButtons(
    radioButtonOptions: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,

) {
    Column {
        radioButtonOptions.forEach { category ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .selectable(
                        selected = (category == selectedCategory),
                        onClick = {
                            onCategorySelected(category)
                        }
                    )
                    .padding(start = 35.dp)
            ) {
                RadioButton(
                    selected = (category == selectedCategory),
                    onClick = { onCategorySelected(category)}
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
