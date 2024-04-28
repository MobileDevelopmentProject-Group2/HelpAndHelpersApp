package com.example.helpersapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.SecondTopBar
import com.example.helpersapp.ui.components.ShowBottomImage

@Composable
fun PrivacyNTermsScreen(
    navController: NavController
) {
    Box {
        ShowBottomImage()
        Scaffold(
            topBar = {
                SecondTopBar(
                    navController,
                ) },
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
                        text = "Privacy and Terms",
                    )

                    Row {
                    }
                    Text(
                        text = stringResource(R.string.privacy_policy_content),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Column {
                        Text(
                            text = "Data Usage",
                            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                            //modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text =
                                "We may also collect information that your browser sends whenever you visit our Service or when you access the Service by or through a mobile device." +
                                "TRACKING and COOKIES DATA"+
                                "We use cookies and similar tracking technologies to track the activity on our Service and hold certain information."+
                                "Cookies are files with a small amount of data which may include an anonymous unique identifier. Cookies are sent to your browser from a website and stored on your device. Tracking technologies also used are beacons, tags, and scripts to collect and track information and to improve and analyze our Service"+
                                "You can instruct your browser to refuse all cookies or to indicate when a cookie is being sent. However, if you do not accept cookies, you may not be able to use some portions of our Service",
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
