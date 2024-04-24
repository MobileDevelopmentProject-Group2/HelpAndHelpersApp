package com.example.helpersapp.ui.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfirmDeleteUserPostDialog(
    navController: NavController,
    postId: String
) {
    val helpViewModel = HelpViewModel()

    AlertDialog(
        onDismissRequest = { navController.navigateUp() },
        icon = { Icon(imageVector = Icons.Outlined.Warning, contentDescription = "warning") },
        title = { Text("Delete This post?") },
        confirmButton = {
            Button(
                onClick = {
                    helpViewModel.deleteUserHelpPost(postId)
                    helpViewModel.emptyFilteredUserHelpPost()
                    navController.navigate("myHelpPostScreen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    navController.navigate("myHelpPostScreen")
                },
                modifier = Modifier
                    .padding(end = 20.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}