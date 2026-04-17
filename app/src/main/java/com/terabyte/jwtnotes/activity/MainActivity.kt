package com.terabyte.jwtnotes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.terabyte.jwtnotes.ui.navigation.Route
import com.terabyte.jwtnotes.ui.screen.CreateNoteScreen
import com.terabyte.jwtnotes.ui.screen.ListNotesScreen
import com.terabyte.jwtnotes.ui.screen.LoginScreen
import com.terabyte.jwtnotes.ui.screen.RegistrationScreen
import com.terabyte.jwtnotes.ui.screen.UpdateNoteScreen
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JwtNotesTheme {
                val navController = rememberNavController()
                Scaffold { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.LoginRoute.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {

                        composable(Route.LoginRoute.route) {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate(Route.ListNotesRoute.route)
                                },
                                onNavigateToRegistration = {
                                    navController.navigate(Route.RegistrationRoute.route)
                                }
                            )
                        }

                        composable(Route.RegistrationRoute.route) {
                            RegistrationScreen(
                                onRegistrationSuccess = {
                                    navController.navigate(Route.ListNotesRoute.route)
                                },
                                onNavigateToLogin = {
                                    navController.navigate(Route.LoginRoute.route)
                                }
                            )
                        }

                        composable(Route.ListNotesRoute.route) {
                            ListNotesScreen()
                        }

                        composable(Route.CreateNoteRoute.route) {
                            CreateNoteScreen()
                        }

                        composable(Route.UpdateNoteRoute.route) {
                            UpdateNoteScreen()
                        }
                    }
                }
            }
        }
    }
}
