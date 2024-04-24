package com.example.helpersapp.ui.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.model.HelpNeeded
import com.example.helpersapp.viewModel.HelpViewModel

@Composable
fun ConfirmDeletePost(
    helpPost: HelpNeeded,
    helpViewModel: HelpViewModel,
    onDismiss:() ->Unit,
    onConfirm: () -> Unit,
    navController: NavController,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(imageVector = Icons.Outlined.Warning, contentDescription = "warning") },
        title = { Text("Delete Post?") },
        text = { Text("Are you sure you want to delete your help post") },
        confirmButton = {
            Button(
                onClick = {
                    //helpViewModel.deleteUserHelpPost(helpPost.id, helpPost.userId)
                    onConfirm()
                    //after the screen for post done, it will stay at post
                    navController.navigate("main")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .padding(end = 25.dp)
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(end = 20.dp)
            ) {
                Text("Cancel")
            }
        }
    )}

