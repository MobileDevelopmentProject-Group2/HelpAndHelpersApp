package com.example.helpersapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.model.User
import com.example.helpersapp.ui.components.MainTopBar
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.example.helpersapp.viewModel.UpdateUserViewModel
import com.example.helpersapp.viewModel.UsersViewModel
import androidx.compose.material3.Text as Text


@Composable
fun NyDataScreen(
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
    var firstname by remember { mutableStateOf(user.firstname) }
    var lastname by remember { mutableStateOf(user.lastname) }
    var email by remember { mutableStateOf(user.email) }
    var address by remember { mutableStateOf(user.address) }
    var password by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

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
                            value = email ,
                            onValueChange = {email = it },
                            label = { Text("Email address")
                            })
                        OutlinedTextField(
                            value = address,
                            onValueChange = {address = it },
                            label = { Text("Address")
                            })
                        OutlinedTextField(
                            value = password,
                            onValueChange = {password = it },
                            label = { Text("New password")
                            })
                        OutlinedTextField(
                            value = currentPassword,
                            onValueChange = {currentPassword = it },
                            label = { Text("Current Password (Required for Change)")
                            })
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(

                            onClick = {
                                //try new code
                                updateUserViewModel.verifyPwd(user.email, currentPassword) {
                                        isPwdCorrect ->
                                    if(isPwdCorrect) {
                                        val updateUser = User(firstname, lastname, email, address)
                                        updateUserViewModel.updateUserDetail(user.uid, updateUser){
                                                isSuccess, updateMessage ->
                                            message = updateMessage
                                            if(isSuccess && password.isNotBlank()) {
                                                updateUserViewModel.updateUserEmailAndPwd(email, password) {
                                                        isPwdUpdateSuccess, pwdUpdateMessage ->
                                                    message = pwdUpdateMessage

                                                }
                                            }
                                        }
                                    }else {
                                        message = "Current password is incorrect"
                                    }

                                }
                                //old code
                                //val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@Button
                                //val updatedUser = User(firstname, lastname, email, address, user.username)
                                //updateUserViewModel.updateUserDetails(userId, updatedUser) { success, message ->
                            }

                        ) {
                            Text("Update")
                        }

                        Button(
                            onClick = { navController.navigate("register") },
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

    Box {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {



        }
    }

 }

