package com.example.helpersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.helpersapp.ui.theme.HelpersAppTheme
import com.example.helpersapp.viewModel.UsersViewModel
import androidx.navigation.compose.rememberNavController
import com.example.helpersapp.ui.screens.AddNewHelpScreen
import com.example.helpersapp.ui.screens.HelpDetailsScreen
import com.example.helpersapp.ui.screens.LandingScreen
import com.example.helpersapp.ui.screens.LoginScreen
import com.example.helpersapp.ui.screens.MainScreen
import com.example.helpersapp.ui.screens.PostNewHelperDetailsScreen
import com.example.helpersapp.ui.screens.RegisterScreen
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.HelperViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        val helpViewModel = ViewModelProvider(this)[HelpViewModel::class.java]
        val helperViewModel = ViewModelProvider(this)[HelperViewModel::class.java]



        setContent {
            HelpersAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppLayout(
                        usersViewModel,
                        helpViewModel,
                        helperViewModel
                    )
                }
            }
        }
    }
}
@Composable
fun AppLayout(usersViewModel: ViewModel, helpViewModel: HelpViewModel, helperViewModel: HelperViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            LandingScreen(
                navController
            )
        }
        composable("main") {
            MainScreen(
                navController,
                usersViewModel,
                helpViewModel
            )
        }
        composable("login") {
            LoginScreen(
                navController,
                usersViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                navController,
                usersViewModel
            )
        }
        composable("addHelp") {
            AddNewHelpScreen(
                navController,
                helpViewModel
            )
        }

        composable("postHelper") {
            PostNewHelperDetailsScreen(
                navController,
                helperViewModel
            )
        }

        composable("helpDetails") {
            HelpDetailsScreen(
                navController,
                helpViewModel
            )
        }
    }
}
