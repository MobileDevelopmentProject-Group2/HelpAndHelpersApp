package com.example.helpersapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.helpersapp.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import com.example.helpersapp.viewModel.HelpViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navController: NavController,
    drawerState: DrawerState,
    scope: kotlinx.coroutines.CoroutineScope,
    loginViewModel: LoginViewModel,
    helpViewModel: HelpViewModel
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }}
            ) {
                Icon(Icons.Filled.Menu, contentDescription = "menu")
            }
        },
        title = {
            Row {
                ClickableText(
                    text = AnnotatedString("Post new help request"),
                    style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                    onClick = { navController.navigate("addHelp") },
                    softWrap = true,
                    modifier = Modifier
                        .padding(15.dp)
                        .width(80.dp)
                )
                ClickableText(
                    text = AnnotatedString("Register as new helper"),
                    style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                    onClick = {navController.navigate("postHelper")},
                    softWrap = true,
                    modifier = Modifier
                        .padding(15.dp)
                        .width(80.dp)
                )
            }
        },
        actions = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(end = 10.dp)

            ) {
                IconButton(
                    onClick = {
                        helpViewModel.emptyHelpList()
                        helpViewModel.emptyNewHelpNeeded()
                        helpViewModel.setCategory("")
                        loginViewModel.logoutUser()
                        navController.navigate("home")
                    },
                ) {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "logout")
                }
                Text(
                    text = "Logout",
                    modifier = Modifier.offset(y = (-10).dp),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}