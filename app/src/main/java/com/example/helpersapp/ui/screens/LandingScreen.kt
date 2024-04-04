package com.example.helpersapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R



@Composable
fun LandingScreen(navController: NavController) {
    LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.top_background),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = null,
            modifier = Modifier.height(130.dp)
        )
        Text(
            text = stringResource(id = R.string.welcome_message),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
                .padding(horizontal = 15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )

        ) {
            Text(stringResource(id = R.string.log_in))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
                .padding(horizontal = 15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
        {
            Text(stringResource(id = R.string.register))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.slogan),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center

        )

        TextButton(onClick = { navController.navigate("register") }) {
            Text(stringResource(id = R.string.privacy_policy), style = MaterialTheme.typography.bodyLarge)
        }


        SodaLogo2()
    }
}

@Composable
fun SodaLogo2() {
    val socialLogos = listOf(R.mipmap.facebook, R.mipmap.google, R.mipmap.twitter)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        socialLogos.forEach { logoRes ->
            IconButton(onClick = { /* TODO: Add action for social media */ }) {
                Image(
                    painter = painterResource(id = logoRes),
                    contentDescription = "Social media",
                )
            }
        }
    }
}



/*
{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Landing Screen"
        )
        Button(onClick = { navController.navigate("login") }) {
            Text(text = "To Login Screen")
        }
        Button(onClick = { navController.navigate("register") }) {
            Text(text = "To Register Screen")
        }
        Button(onClick = { navController.navigate("main") }) {
            Text(text = "To Main Screen")
        }
        Button(onClick = { navController.navigate("addHelp") }) {
            Text(text = "To Add New Help Screen")
        }
    }
}
*/