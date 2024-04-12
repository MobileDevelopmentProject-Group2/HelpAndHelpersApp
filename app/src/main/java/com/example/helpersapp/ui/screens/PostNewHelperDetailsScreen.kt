package com.example.helpersapp.ui.screens

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.graphics.painter.rememberImagePainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.createUsername
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.example.helpersapp.viewModel.UsersViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID


@Composable
fun UploadImageAndCertificateToStorage(navController: NavController, helperViewModel: HelperViewModel,) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var certificateUri by remember { mutableStateOf<Uri?>(null) }

    // Create an ActivityResultLauncher to handle image selection actions
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    // Create an ActivityResultLauncher to handle certificate selection actions
    val certificateLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        certificateUri = uri
    }
    Column {
        // Image preview
        imageUri?.let { uri ->
            Box(modifier = Modifier.padding(vertical = 8.dp)) {
/*
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                )

 */
            }
        }
        Button(onClick = { imageLauncher.launch("image/*") }) {
            Text("Select Profile Picture")
        }

        Button(onClick = { certificateLauncher.launch("application/pdf") }) {
            Text("Select Certificate")
        }

        Button(onClick = {
            imageUri?.let { helperViewModel.uploadProfilePicture(it) }
            certificateUri?.let { helperViewModel.uploadCertificate(it) }
        })  {
            Text("Upload")
        }
    }
}
@Composable
fun CategorySelectionRow(
    modifier: Modifier = Modifier,
    onCategorySelected: (Set<String>) -> Unit
) {
    var selectedCategories by remember { mutableStateOf(emptySet<String>()) }

    Column(modifier = modifier) {
        Text(
            text = "Choose the category for the expertise you are offering:",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Checkbox(
                checked = "Nanny" in selectedCategories,
                onCheckedChange = { isChecked ->
                    selectedCategories = if (isChecked) {
                        selectedCategories + "Nanny"
                    } else {
                        selectedCategories - "Nanny"
                    }
                    onCategorySelected(selectedCategories)
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Nanny")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = "Tutor" in selectedCategories,
                onCheckedChange = { isChecked ->
                    selectedCategories = if (isChecked) {
                        selectedCategories + "Tutor"
                    } else {
                        selectedCategories - "Tutor"
                    }
                    onCategorySelected(selectedCategories)
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Tutor")
        }
    }
}
@Composable
fun PostNewHelperDetailsScreen(
    usersViewModel: UsersViewModel,
    navController: NavController,
    helperViewModel: HelperViewModel,
    loginViewModel: LoginViewModel,
    ) {

    var about by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var helpdetails by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    val useremail = Firebase.auth.currentUser?.email
    val username = createUsername(useremail ?: "")
    val user by loginViewModel.userDetails.collectAsState()
    //val username = user.username,

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
                text = "Hi,${user.firstname}!",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.post_your_area_of_expertise),
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleLarge,
            )
            Column {
                Text(
                    text = stringResource(R.string.tell_shortly_about_yourself),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    value = about,
                    onValueChange = { about = it },
                    shape = MaterialTheme.shapes.medium
                )
            }

            CategorySelectionRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                onCategorySelected = { category = it.joinToString(", ") }
            )

            Text(
                text = stringResource(R.string.describe_in_more_details),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                value = helpdetails,
                onValueChange = { helpdetails = it },
                shape = MaterialTheme.shapes.medium
            )

            Text(
                text = stringResource(R.string.previous_experience),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                value = experience,
                onValueChange = { experience = it },
                shape = MaterialTheme.shapes.medium
            )

            UploadImageAndCertificateToStorage(navController,helperViewModel)

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigateUp() },
                    shape = MaterialTheme.shapes.extraLarge,
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
                        if (username != null) {
                            helperViewModel.saveUserData(
                                about,
                                category,
                                helpdetails,
                                experience,
                                username,
                                onSuccess = {
                                    Log.d(TAG, "DocumentSnapshot successfully written!")
                                    navController.navigate("helperDetailsScreen")
                                },
                                onFailure = { e ->
                                    Log.e(TAG, " an error while saving data to Firestore.", e)
                                }
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(52.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Post to confirm")
                }
            }
        }
    }
}


