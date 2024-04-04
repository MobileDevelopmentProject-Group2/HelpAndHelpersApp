package com.example.helpersapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.helpersapp.R
import com.example.helpersapp.ui.components.ListAllHelpNeeded
import com.example.helpersapp.ui.components.MainTopBar
import com.example.helpersapp.ui.components.ShowBottomImage
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.UsersViewModel

@Composable
fun MainScreen(
    navController: NavController,
    usersViewModel: UsersViewModel,
    helpViewModel: HelpViewModel,
    helperViewModel: HelperViewModel)
{
    val helpList by helpViewModel.helpList.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    helpViewModel.getAllHelpRequests()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("CareConnect app", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                    label = { Text(text = "Application rights") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null) },
                    label = { Text(text = "My data") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    icon = { Icon(imageVector = Icons.Outlined.Phone, contentDescription = null) },
                    label = { Text(text = "About / contact") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
            }
        }) {
        Box {
            ShowBottomImage()
            Scaffold(
                topBar = { MainTopBar(navController, drawerState, scope) },
                containerColor = Color.Transparent,
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(bottom = 150.dp, start = 30.dp, end = 30.dp, top = 30.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleLarge,
                            text = "Welcome to CareConnect!",
                        )
                        Text(
                            text = "Get a list of all helper persons by category:",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )
                        Row {
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .width(150.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "List all nannies")

                                    Image(
                                        painter = painterResource(id = R.drawable.baby_face_icon),
                                        contentDescription = "child",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .width(150.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "List all tutors")
                                    Image(
                                        painter = painterResource(id = R.drawable.reading),
                                        contentDescription = "reading",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Get list of all help requests by category:",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 30.dp, bottom = 5.dp)
                        )
                        Row {
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .width(150.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Nanny needed")
                                    Image(
                                        painter = painterResource(id = R.drawable.baby_face_icon),
                                        contentDescription = "child",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .width(150.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Tutor needed")
                                    Image(
                                        painter = painterResource(id = R.drawable.reading),
                                        contentDescription = "reading",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "List of all help requests:",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 30.dp, bottom = 5.dp)

                        )
                        if (helpList.isNotEmpty()) {
                            ListAllHelpNeeded(helpList, helpViewModel, navController)
                        }

                        Spacer(modifier = Modifier.padding(50.dp))

                        //here are the buttons to navigate to other screens; we can remove these later
                        Button(onClick = { navController.navigate("home") }) {
                            Text(text = "to landing screen")
                        }
                        Button(onClick = { navController.navigate("login") }) {
                            Text(text = "To Login Screen")
                        }
                        Button(onClick = { navController.navigate("register") }) {
                            Text(text = "To Register Screen")
                        }
                        Button(onClick = { navController.navigate("addHelp") }) {
                            Text(text = "To Add New Help Screen")
                        }
                        Button(onClick = { navController.navigate("helpDetails") }) {
                            Text(text = "To Help Details Screen")
                        }
                        Button(onClick = { navController.navigate("postHelper") }) {
                            Text(text = "To Post New Helper Details Screen")
                        }
                    }
                }
            )
        }
        Button(onClick = { navController.navigate("profile") }) {
            Text(text = "profile")
        }
    }
}