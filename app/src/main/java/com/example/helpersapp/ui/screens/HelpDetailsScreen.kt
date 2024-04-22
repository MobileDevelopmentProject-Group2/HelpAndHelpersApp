package com.example.helpersapp.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.HelpDetailsItem
import com.example.helpersapp.ui.components.MapActivity
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.getLocationByPostalCode
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel

@Composable
fun HelpDetailsScreen(navController: NavController, helpViewModel: HelpViewModel, loginViewModel: LoginViewModel) {
    val screenState by helpViewModel.helpDetailsScreenState.collectAsState()
    val helpDetails by helpViewModel.newHelpNeeded.collectAsState()
    val context = LocalContext.current
    var showMap by rememberSaveable { mutableStateOf(false) }
    val coordinates = getLocationByPostalCode(context, helpDetails.postalCode)

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
                text = stringResource(R.string.details_of_help_needed),
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )

            HelpDetailsItem(helpDetails)

            if (screenState != "confirm" && coordinates != null) {
                Button(
                    onClick = { showMap = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                ) {
                    Text(text = "Show approximate location on map")
                }
            }
            if (showMap) {
                Column(modifier = Modifier.height(400.dp)) {
                    MapActivity(
                        coordinates?.areaName ?: "",
                        coordinates?.latitude ?: 0.0,
                        coordinates?.longitude ?: 0.0
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (screenState == "contact") {
                            helpViewModel.emptyNewHelpNeeded()
                        }
                        helpViewModel.setHelpDetailsScreenState("")
                        showMap = false
                        navController.navigateUp()
                    },
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
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = stringResource(R.string.back_))
                }
                if (screenState == "confirm") {
                    Button(
                        onClick = {
                            helpViewModel.addNewHelpToCollection()
                            navController.navigate("main")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .height(52.dp)
                            .width(150.dp)
                    ) {
                        Text(text = stringResource(R.string.confirm))
                    }
                } else {
                    Button(
                        onClick = {
                            context.sendMail(
                                to = helpDetails.userEmail,
                                subject = "Contact request from CareConnect"
                            ) {
                                navController.navigate("main")
                                Toast.makeText(context, "Email sending successful", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .height(52.dp)
                            .width(150.dp)
                    ) {
                        Text(text = "Send mail")
                    }
                }
            }
        }
    }
}
fun Context.sendMail(
    to: String,
    subject: String,
    onSuccess: () -> Unit
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
        onSuccess.invoke()
    } catch (e: ActivityNotFoundException) {
        // Handle case where no email app is available
        Toast.makeText(this, "You need to have an emailing application available", Toast.LENGTH_SHORT).show()
    } catch (t: Throwable) {
        // Handle potential other type of exceptions
        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
    }
}