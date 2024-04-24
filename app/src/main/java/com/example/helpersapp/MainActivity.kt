package com.example.helpersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.helpersapp.ui.screens.AboutAndContactScreen
import com.example.helpersapp.ui.screens.AddNewHelpScreen
import com.example.helpersapp.ui.screens.HelpByCategoryScreen
import com.example.helpersapp.ui.screens.HelpDetailsScreen
import com.example.helpersapp.ui.screens.HelperDetailsScreen
import com.example.helpersapp.ui.screens.HelperListingScreen
import com.example.helpersapp.ui.screens.LandingScreen
import com.example.helpersapp.ui.screens.LoginScreen
import com.example.helpersapp.ui.screens.MainScreen
import com.example.helpersapp.ui.screens.MyDataScreen
import com.example.helpersapp.ui.screens.PostNewHelperDetailsScreen
import com.example.helpersapp.ui.screens.PrivacyNTermsScreen
import com.example.helpersapp.ui.screens.RegisterScreen
import com.example.helpersapp.ui.theme.HelpersAppTheme
import com.example.helpersapp.viewModel.HelpViewModel

import com.example.helpersapp.viewModel.LoginViewModel

import com.example.helpersapp.ui.components.createUsername
import com.example.helpersapp.ui.screens.HelpByCategoryScreen
import com.example.helpersapp.ui.screens.MyHelpPostScreen
import com.example.helpersapp.viewModel.HelperViewModel
import com.example.helpersapp.viewModel.UpdateUserViewModel
import com.example.helpersapp.viewModel.UsersViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        val helpViewModel = ViewModelProvider(this)[HelpViewModel::class.java]
        val helperViewModel = ViewModelProvider(this)[HelperViewModel::class.java]
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val updateUserViewModel = ViewModelProvider(this)[UpdateUserViewModel::class.java]

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
                        helperViewModel,
                        loginViewModel,
                        updateUserViewModel
                    )
                }
            }
        }
    }
}
@Composable
fun AppLayout(
    usersViewModel: UsersViewModel,
    helpViewModel: HelpViewModel,
    helperViewModel: HelperViewModel,
    loginViewModel: LoginViewModel,
    updateUserViewModel: UpdateUserViewModel
)
{
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
                helpViewModel,
                helperViewModel,
                loginViewModel,
            )
        }
        composable("login") {
            LoginScreen(
                navController,
                loginViewModel
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
        composable("helpDetails") {
            HelpDetailsScreen(
                navController,
                helpViewModel,
                loginViewModel
            )
        }
        composable("postHelper") {
            PostNewHelperDetailsScreen(
                navController,
                helperViewModel,
                loginViewModel
            )
        }
        composable("privacy") {
            PrivacyNTermsScreen(
                navController
            )
        }
        //add about screen
        composable("about") {
            AboutAndContactScreen(
                navController,
            )
        }
        composable("helpByCategory") {
            HelpByCategoryScreen(
                navController,
                helpViewModel
            )
        }
        //user profit for testing
        composable("my_data") {
            MyDataScreen(
                navController,
                updateUserViewModel,
                loginViewModel,
                helpViewModel
            )
        }
        composable("helperDetailsScreen") {
            HelperDetailsScreen(
                navController,
                helperViewModel,
                loginViewModel,
            )
        }

        //get my help post
        composable("myHelpPostScreen") {
            MyHelpPostScreen(
                navController,
                helpViewModel,
                loginViewModel
            )
        }
        composable("helperListingScreen") {
            HelperListingScreen(
                navController,
                helperViewModel,
                loginViewModel,

            )
        }
    }
}





