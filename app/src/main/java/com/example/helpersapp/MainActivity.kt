package com.example.helpersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.helpersapp.ui.screens.RegisterScreen
import com.example.helpersapp.viewModel.HelpViewModel
import com.example.helpersapp.viewModel.LoginViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helpersapp.viewModel.HelperViewModel
import com.google.firebase.Firebase
import com.google.firebase.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        val helpViewModel = ViewModelProvider(this)[HelpViewModel::class.java]
        val helperViewModel = ViewModelProvider(this)[HelperViewModel::class.java]
        //add login viewmodel
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        //Firebase.initialize(this)

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
                        loginViewModel)
                }
            }
        }
    }
}



@Composable
//below old code, try to do some changes
//fun AppLayout(usersViewModel: ViewModel, helpViewModel: HelpViewModel) {
fun AppLayout(usersViewModel: UsersViewModel, helpViewModel: HelpViewModel, loginViewModel: LoginViewModel)
{
    val navController = rememberNavController()
//new code
    val usersViewModel: UsersViewModel = viewModel()
    val helpViewModel: HelpViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
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
                //usersViewModel,
                //add login viewmodel
                loginViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                navController,
                usersViewModel
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

/*
        composable("addHelp") {
            AddNewHelpScreen(
                navController,
                helpViewModel
            )
        }
* */
