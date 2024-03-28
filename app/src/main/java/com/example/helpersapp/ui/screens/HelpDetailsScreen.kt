package com.example.helpersapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.ui.components.HelpDetailsItem
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelpViewModel

@Composable
fun HelpDetailsScreen(navController: NavController, helpViewModel: HelpViewModel) {
    val helpDetails by helpViewModel.newHelpNeeded.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ShowBottomImage()
        Column(
            modifier = Modifier
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Details of Help Needed",
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )

            HelpDetailsItem(helpDetails)

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigateUp() },
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
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Back")
                }
                Button(
                    onClick = {
                        helpViewModel.addNewHelpToCollection()
                        navController.navigate("main")
                    },
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
                    Text(text = "Confirm")
                }
            }
        }
    }

}