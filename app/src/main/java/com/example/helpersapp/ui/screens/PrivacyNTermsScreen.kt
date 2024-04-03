package com.example.helpersapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.ShowBottomImage


@Composable
fun PrivacyNTermsScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ShowBottomImage()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Text(
                text = "Privacy & Terms",
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.privacy_policy_content),
                style = MaterialTheme.typography.bodyLarge)
            Column {
                Text(
                    text = stringResource(R.string.tell_shortly_about_yourself),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }


            }
            Button(
                onClick = { navController.navigate("register") },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(52.dp)
                    .width(150.dp)
            ) {
                Text(text = "Back")
            }

        }
    }



