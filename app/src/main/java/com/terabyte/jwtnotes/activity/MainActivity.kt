package com.terabyte.jwtnotes.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

                NavHost(
                    navController = navController,
                    startDestination = Route.LoginRoute.route
                ) {

                    composable(Route.LoginRoute.route) {
                        LoginScreen() {
                            navController.navigate(Route.ListNotesRoute.route)
                        }
                    }

                    composable(Route.RegistrationRoute.route) {
                        RegistrationScreen()
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
