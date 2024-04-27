package com.example.helpersapp.ui.screens

import android.graphics.fonts.FontStyle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helpersapp.model.User
import com.example.helpersapp.ui.components.ConfirmDeleteDialog
import com.example.helpersapp.ui.components.MainTopBar
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.ui.components.createUsername
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import com.example.helpersapp.viewModel.UpdateUserViewModel


@Composable
fun MyDataScreen(
    navController: NavController,
    updateUserViewModel: UpdateUserViewModel,
    loginViewModel: LoginViewModel,
    helpViewModel: HelpViewModel,
    helperViewModel: HelperViewModel
)
{
    helperViewModel.checkIfHelper()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val user by loginViewModel.userDetails.collectAsState()
    var firstname by remember { mutableStateOf(user.firstname) }
    var lastname by remember { mutableStateOf(user.lastname) }
    var email by remember { mutableStateOf(user.email) }
    var address by remember { mutableStateOf(user.address) }
    val userId = createUsername(email)
    val openAlertDialog = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val isHelper by helperViewModel.isUserAHelper.collectAsState()
    var showEmailNotice by remember { mutableStateOf(false) }

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
        }){
         ShowBottomImage()
         Scaffold (
             topBar = {
                 MainTopBar(
                     navController,
                     drawerState,
                     scope,
                     loginViewModel,
                     helpViewModel,
                     helperViewModel
                 )},
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

                     Spacer(modifier = Modifier.height(16.dp))
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(horizontal = 16.dp),
                         horizontalArrangement = Arrangement.SpaceAround
                     ) {
                     Button(
                         onClick = {
                         val updateUser = User(
                             firstname = firstname,
                             lastname = lastname,
                             //email = email,
                             address = address
                         )
                         updateUserViewModel.updateUserDetail(userId, updateUser) {
                                 success, message ->
                             if (success) {
                                 Toast.makeText(
                                     context,
                                     "User profile updated",
                                     Toast.LENGTH_LONG
                                 ).show()
                                 Log.e("FirebaseStorage", "update user data")
                             }else {
                                 Toast.makeText(
                                     context,
                                     "Fail to update user profile, check again",
                                     Toast.LENGTH_LONG
                                 ).show()
                                 Log.e("FirebaseStorage", "faile",)
                             }
                         }
                     },
                         modifier = Modifier.width(150.dp),
                         colors = ButtonDefaults.buttonColors(
                             containerColor = MaterialTheme.colorScheme.primaryContainer,
                             contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                         )
                     ) {
                         Text("Update")
                     }
                     Spacer(modifier = Modifier.width(16.dp))
                     Button(
                         onClick = { openAlertDialog.value = true },
                         colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                         modifier = Modifier.width(150.dp)
                    )
                     {
                         Text("Delete User")
                     }
                     if (openAlertDialog.value) {
                         ConfirmDeleteDialog(
                            loginViewModel = loginViewModel,
                            navController = navController
                        )
                     }}
                     Spacer(modifier = Modifier.height(36.dp))
                     ClickableText(
                         text = AnnotatedString("Change password"),
                         onClick = {
                             loginViewModel.sendPasswordResetEmail(
                                 user.email,
                                 onSuccess = {
                                     Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                        showEmailNotice = true
                                 },
                                 onFailure = {
                                     Toast.makeText(context, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                                }
                             ) },
                         style = TextStyle(
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                                fontSize = 20.sp,
                                textDecoration = TextDecoration.Underline
                         ),
                     )
                     Spacer(modifier =  Modifier.height(16.dp))
                        if (showEmailNotice) {
                            Text(
                                text = "Password reset email sent",
                                style = TextStyle(
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color.Magenta
                                )
                            )
                        }
                     Spacer(modifier = Modifier.height(36.dp))
                     Column(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(horizontal = 16.dp),
                         horizontalAlignment = Alignment.CenterHorizontally
                     ) {
                         if (isHelper) {
                             Button(
                                 onClick = { navController.navigate("helperDetailsScreen") },
                                 modifier = Modifier.width(150.dp),
                                 colors = ButtonDefaults.buttonColors(
                                     containerColor = MaterialTheme.colorScheme.primaryContainer,
                                     contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )

                             ) {
                                 Text("My helper data")
                             }
                         }
                         Spacer(modifier = Modifier.width(8.dp))
                         Button(
                             onClick = { navController.navigate("myHelpPostScreen")},
                             modifier = Modifier.width(150.dp),
                             colors = ButtonDefaults.buttonColors(
                                 containerColor = MaterialTheme.colorScheme.primaryContainer,
                                 contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                         )
                         {
                             Text("My help post")
                         }
                     }
                     Spacer(modifier = Modifier.height(48.dp))
                     Column(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start
                     ) {
                         Button(
                             onClick = {
                                 navController.navigate("main")
                                 showEmailNotice = false
                                },
                             modifier = Modifier.width(100.dp),
                             colors = ButtonDefaults.buttonColors(
                                 containerColor = MaterialTheme.colorScheme.primaryContainer,
                                 contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                             )
                         ) {
                             Text(text = "Back")
                         }
                     }
                }
            })
        }
    }




