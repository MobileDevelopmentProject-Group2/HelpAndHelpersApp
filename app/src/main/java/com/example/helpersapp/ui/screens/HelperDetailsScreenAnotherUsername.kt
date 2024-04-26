package com.example.helpersapp.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.helpersapp.R
import com.example.helpersapp.model.HelperInfo
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.HelperDetail
import com.example.helpersapp.ui.components.ShowHelperRating
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


@Composable
fun HelperDetailsScreenAnotherUsername(
    navController: NavController,
    helperViewModel: HelperViewModel,
    loginViewModel: LoginViewModel,
) {
    val helperInfo = remember { mutableStateOf(HelperInfo("", "", "", "", "","")) }
    val clickedUsername  by helperViewModel.clickedUsername.collectAsState()
    Log.d("***", "${clickedUsername}")
    val userfullnameState = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(clickedUsername) {
        helperViewModel.getFullUserName(
            clickedUsername,
            onSuccess = { fullName ->
                userfullnameState.value = fullName
            },
            onFailure = { e ->
                Log.e("***", "Error fetching user full name: ${e.message}")
            }
        )
    }
    LaunchedEffect(Unit) {
        helperViewModel.getHelperDetails(
            username = clickedUsername,
            onSuccess = { info ->
                helperInfo.value = info
            },
            onFailure = { e ->
                // Handle failure
                Log.e("***", "Error fetching helper details: ${e.message}")
            }
        )
    }
    val username = clickedUsername
    val url = "gs://careconnect-65e41.appspot.com/"
    var byteArray by remember { mutableStateOf<ByteArray?>(null) }

    LaunchedEffect(url) {
        Firebase.storage.reference
            .child("$username/profile_picture.jpg")
            .getBytes(1024 * 1024)
            .addOnSuccessListener { fetchedBytes ->
                byteArray = fetchedBytes
                Log.d("HelperDetails", "ByteArray: $fetchedBytes")
                Log.d("***", "Image successfully fetched. Image byte array size: ${fetchedBytes.size}.Fetching image from URL: $url")
            }
            .addOnFailureListener { exception ->
                Log.e("***", "Error fetching image: ${exception.message}")
                byteArray = null
            }
    }
    LaunchedEffect(username) {
        helperViewModel.getHelperRating(username)
    }
    val rating by helperViewModel.helperRating.collectAsState()


    Box(modifier = Modifier.fillMaxSize()) {
        ShowBottomImage()

        Column(
            modifier = Modifier
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            byteArray?.let {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = byteArray
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userfullnameState.value,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            ShowHelperRating(rating)
            if (rating == 0f) {
                Text(
                    text = "No ratings yet for this helper",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // To helper rating
            val fullName = userfullnameState.value
            Button(
                onClick = { navController.navigate("helperRating/$fullName/$username") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
            ) {
                Text(text = "Give me your stars and rate me here!")
            }

            HelperDetail(helperInfo.value)

            Text(
                text = "Certification list : "
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        helperViewModel.setHelperRating()
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

                    Button(
                        onClick = {
                            helperViewModel.getEmailForClickedUser(
                                onSuccess = { email ->
                                    sendMail(
                                        context = context,
                                        to = email,
                                        subject = "Contact request from CareConnect"
                                    ) {
                                        navController.navigate("main")
                                        Toast.makeText(context, "Email sending successful", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onFailure = { e ->
                                    Log.e("***", "Error fetching email: ${e.message}")
                                    Toast.makeText(context, "Failed to fetch email", Toast.LENGTH_SHORT).show()
                                }
                            )
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

fun sendMail(
    context: Context,
    to: String?,
    subject: String,
    onSuccess: () -> Unit
) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        context.startActivity(intent)
        onSuccess.invoke()
    } catch (e: ActivityNotFoundException) {
        // Handle case where no email app is available
        Toast.makeText(context, "You need to have an emailing application available", Toast.LENGTH_SHORT).show()
    } catch (t: Throwable) {
        // Handle potential other type of exceptions
        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
    }
}