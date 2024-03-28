package com.example.helpersapp.ui.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.viewModel.HelperViewModel
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ShowIMage1() {
    Image(
        painter = painterResource(id = R.drawable.bottom_background_with_logo),
        contentDescription = "Background image of the bottom bar",
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
    )
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
fun PostNewHelperDetailsScreen(navController: NavController, helperViewModel: HelperViewModel) {

    var about by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    //var username by remember { mutableStateOf("") }
    // username for developing status
    val username = "dev_user3"
/*
    // get username by Firebase Authentication
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val username = currentUser?.displayName ?: "unknown_user"

 */

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ShowIMage1()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 150.dp, start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
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
                value = experience,
                onValueChange = { experience = it },
                shape = MaterialTheme.shapes.medium
            )

            Button(
                onClick = {
                    try {
                        saveUserData(about, category, experience, username)
                    } catch (e: Exception) {
                        Log.e(TAG, " an error while saving data to Firestore.", e)
                    }
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    contentColor = Color.Black,
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(52.dp)
                    .width(210.dp)
            ) {
                Text(text = "Post")
            }
        }
    }
}

fun saveUserData(about: String, category: String, experience: String, username: String) {
    val db = FirebaseFirestore.getInstance()

    val userData = hashMapOf(
        "about" to about,
        "category" to category,
        "experience" to experience,
        "username" to username
    )

    db.collection("helpers").document(username)
        .set(userData)
        .addOnSuccessListener {
            Log.d(TAG, "DocumentSnapshot successfully written!")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error writing document", e)
        }
}
