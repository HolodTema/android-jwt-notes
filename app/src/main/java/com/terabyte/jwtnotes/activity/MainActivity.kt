package com.terabyte.jwtnotes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.terabyte.jwtnotes.ui.navigation.Route
import com.terabyte.jwtnotes.ui.screen.CreateNoteScreen
import com.terabyte.jwtnotes.ui.screen.ListNotesScreen
import com.terabyte.jwtnotes.ui.screen.LoginScreen
import com.terabyte.jwtnotes.ui.screen.RegistrationScreen
import com.terabyte.jwtnotes.ui.screen.TokenExpiredScreen
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
                                    // navigate to ListNotesRoute and clear all the backstack
                                    navController.navigate(Route.ListNotesRoute.route) {
                                        popUpTo(Route.LoginRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNavigateToRegistration = {
                                    navController.navigate(Route.RegistrationRoute.route) {
                                        popUpTo(Route.LoginRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Route.RegistrationRoute.route) {
                            RegistrationScreen(
                                onRegistrationSuccess = {
                                    // navigate to ListNotesRoute and clear all the backstack
                                    navController.navigate(Route.ListNotesRoute.route) {
                                        popUpTo(Route.RegistrationRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.navigate(Route.LoginRoute.route) {
                                        popUpTo(Route.RegistrationRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Route.ListNotesRoute.route) {
                            ListNotesScreen(
                                onTokenExpired = {
                                    // navigate to TokenExpiredScreen and clear all the backstack
                                    navController.navigate(Route.TokenExpiredRoute.route) {
                                        popUpTo(Route.ListNotesRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onLogout = {
                                    // navigate to LoginScreen and clear all the backstack
                                    navController.navigate(Route.LoginRoute.route) {
                                        popUpTo(Route.ListNotesRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onCreateNote = {
                                    navController.navigate(Route.CreateNoteRoute.route)
                                },
                                onUpdateNote = { noteModel ->
                                    navController.navigate(Route.UpdateNoteRoute(noteModel.id).route)
                                }
                            )
                        }

                        composable(Route.CreateNoteRoute.route) {
                            CreateNoteScreen(
                                onTokenExpired = {
                                    // navigate to TokenExpiredScreen and clear all the backstack
                                    navController.navigate(Route.TokenExpiredRoute.route) {
                                        popUpTo(Route.CreateNoteRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNoteCreated = {
                                    navController.navigate(Route.ListNotesRoute.route) {
                                        popUpTo(Route.CreateNoteRoute.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            route = Route.UpdateNoteRoute.ROUTE_PLACEHOLDER,
                            arguments = listOf(navArgument("noteId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getInt("noteId") ?: error("noteId missing")

                            UpdateNoteScreen(
                                onTokenExpired = {
                                    // navigate to TokenExpiredScreen and clear all the backstack
                                    navController.navigate(Route.TokenExpiredRoute.route) {
                                        popUpTo(Route.UpdateNoteRoute(noteId).route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNoteEditingFinished = {
                                    navController.navigate(Route.ListNotesRoute.route) {
                                        popUpTo(Route.UpdateNoteRoute(noteId).route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Route.TokenExpiredRoute.route) {
                            TokenExpiredScreen {
                                // navigate to LoginScreen and clear all the backstack
                                navController.navigate(Route.LoginRoute.route) {
                                    popUpTo(Route.TokenExpiredRoute.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
