package com.example.helpersapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.model.User
import com.example.helpersapp.ui.components.MainTopBar
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.example.helpersapp.viewModel.UpdateUserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.helpersapp.ui.components.createUsername


@Composable
fun MyDataScreen(
    navController: NavController,
    updateUserViewModel: UpdateUserViewModel,
    //usersViewModel: UsersViewModel,
    loginViewModel: LoginViewModel,
    helpViewModel: HelpViewModel
)
{
    //add dwawerstate
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val user by loginViewModel.userDetails.collectAsState()
    //val user by updateUserViewModel.userDetails.collectAsState()
    var firstname by remember { mutableStateOf(user.firstname) }
    var lastname by remember { mutableStateOf(user.lastname) }
    var email by remember { mutableStateOf(user.email) }
    var address by remember { mutableStateOf(user.address) }
    //var password by remember { mutableStateOf("") }
    //var currentPassword by remember { mutableStateOf("") }
    //var message by remember { mutableStateOf("") }
    val userId = createUsername(email)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("CareConnect app", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                    label = { Text(text = "Privacy policy") },
                    selected = false,
                    onClick = { navController.navigate("privacy") }
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null) },
                    label = { Text(text = "My data") },
                    selected = false,
                    onClick = { navController.navigate("my_data") }
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Phone, contentDescription = null) },
                    label = { Text(text = "About / contact") },
                    selected = false,
                    onClick = { navController.navigate("about") }
                )
            }
        }) {
             ShowBottomImage()
        Scaffold (
            topBar = {
                MainTopBar(
                    navController,
                    drawerState,
                    scope,
                    loginViewModel,
                    helpViewModel
                ) },
                containerColor = Color.Transparent,
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(bottom = 150.dp, start = 30.dp, end = 30.dp, top = 10.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleLarge,
                            text = "User Profile",
                        )
                        OutlinedTextField(
                            value = firstname ,
                            onValueChange = {firstname = it },
                            label = { Text("First Name")
                            } )

                        OutlinedTextField(
                            value = lastname ,
                            onValueChange = {lastname = it },
                            label = { Text("Last Name")
                            } )

                        OutlinedTextField(
                            value = address,
                            onValueChange = {address = it },
                            label = { Text("Address")
                            })

                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                              val updateUser = User(
                                  firstname = firstname,
                                  lastname = lastname,
                                  //email = email,
                                  address = address
                            )
                              updateUserViewModel.updateUserDetail(userId, updateUser) {
                                      success, message ->
                                  if (success) {
                                      Log.e("FirebaseStorage", "update user data")
                                  }else {
                                      Log.e("FirebaseStorage", "faile",)
                                  }
                              }



                          },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Update")
                        }

                        Button(
                            onClick = { navController.navigate("main") },
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

@Composable
fun ConfirmChange(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    AlertDialog(
        onDismissRequest = { navController.navigateUp() },
        icon = { Icon(imageVector = Icons.Outlined.Warning, contentDescription = "warning") },
        title = { Text("Do you want to ") },
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
/*                        OutlinedTextField(
                            value = email ,
                            onValueChange = {email = it },
                            label = { Text("Email address")
                            })
* */