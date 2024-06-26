package com.example.helpersapp.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.ui.components.SecondTopBar
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel

@Composable
fun AboutAndContactScreen(
    navController: NavController,
) {
    Box {
        ShowBottomImage()
        Scaffold(
            topBar = {
                SecondTopBar(
                    navController,
                )
            },
            containerColor = Color.Transparent,
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(bottom = 150.dp, start = 30.dp, end = 30.dp, top = 30.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.titleLarge,
                        text = "About CareConnect",
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                    }
                    Text(
                        text = "At CareConnect, we believe every child deserves the best care and education. " +
                                "We are dedicated to connecting parents with the top nannies and tutors across the country. " +
                                "Through a rigorous screening process, we ensure only the most qualified candidates are matched with families, " +
                                "promoting a safe and nurturing environment for children's growth and learning.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Our Vision",
                            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "To be the most trusted platform for parental support services, " +
                                    "where every parent can find peace of mind knowing their children are in good hands.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Our Missions",
                            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "Helping parents by providing them with a wide range of quality caregivers and educators, " +
                                    "enabling them to balance work and family life more effectively.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
                        Text(
                            text = "Contact Us",
                            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "We're here to help and answer any question you might have. " +
                                    "We look forward to hearing from you.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Email: support@careconnect.com",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Phone: +358 40 123-4567",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Address: 123 CareConnect Lane, Education City, Oulu, 12345",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Button(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .padding(top = 24.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),

                        ) {
                        Text(text = "Back")
                    }
                }
            }
        )
    }
}
