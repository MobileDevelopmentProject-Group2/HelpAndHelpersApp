package com.example.helpersapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MyHelpPostScreen (
    navController: NavController,
    helpViewModel: HelpViewModel,
    loginViewModel: LoginViewModel
    )
{
    val userHelpPost by helpViewModel.userHelpPost.collectAsState()
    val userID = Firebase.auth.currentUser?.uid?:""
    //val userEmail = Firebase.auth.currentUser?.email ?:""

    LaunchedEffect(userID ){
        Log.d("MyHelpPostScreen", "Fetching posts for user ID: $userID")
        if (userID.isNotEmpty()) {
        try {
            helpViewModel.getCurrentUserPost(userID)
        }catch (e : Exception) {
            Log.e("HelperDetailsScreen", "Unhandled exception", e)

        }
        }else {
            Log.d("MyHelpPostScreen", "User ID is empty, not fetching posts")
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)

    ){
        Column (
            modifier = Modifier
                .align(Alignment.TopCenter)
                //.fillMaxWidth()
                .verticalScroll(rememberScrollState()),
                //.padding(16.dp),
            verticalArrangement =  Arrangement.spacedBy(16.dp)
            )

    {
            Text(
                text = "My help post",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if(userHelpPost.isEmpty()) {
                Text(
                    text = "You have no post",
                    modifier = Modifier.padding(16.dp)
                )
                //Spacer(modifier =Modifier.weight(1f) )
            }else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                    items(userHelpPost) {
                            helpPost->
                        MyHelpPostItem(helpNeeded = helpPost )
                    }

                }


                }
            }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { navController.navigate("my_data") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                //.padding(160.dp)
                .width(150.dp)
        ) {
            Text("Back")
        }
        }
}

