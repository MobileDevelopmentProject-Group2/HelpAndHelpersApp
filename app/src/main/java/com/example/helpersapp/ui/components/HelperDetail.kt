package com.example.helpersapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helpersapp.model.HelperInfo

@Composable
fun HelperDetail(helperInfo: HelperInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp, start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "About:\n${helperInfo.about}")
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Category:\n${helperInfo.category}")
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Details:\n${helperInfo.details}")
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Experience:\n${helperInfo.experience}")
    }
}