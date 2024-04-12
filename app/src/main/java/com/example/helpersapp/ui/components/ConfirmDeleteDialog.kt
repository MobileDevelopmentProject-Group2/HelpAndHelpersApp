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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.viewModel.LoginViewModel

@Composable
fun ConfirmDeleteDialog(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    AlertDialog(
        onDismissRequest = { navController.navigateUp() },
        icon = { Icon(imageVector = Icons.Outlined.Warning, contentDescription = "warning") },
        title = { Text("Delete user?") },
        text = { Text("Are you sure you want to delete your data?\nThis will remove all register and user data.") },
        confirmButton = {
            Button(
                onClick = {
                    loginViewModel.deleteUser()
                    navController.navigate("home")
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
                onClick = {
                    navController.navigate("main")
                },
                modifier = Modifier
                    .padding(end = 20.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}