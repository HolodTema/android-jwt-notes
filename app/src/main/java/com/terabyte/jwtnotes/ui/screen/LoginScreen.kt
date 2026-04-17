package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.stateFlowLoginScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = "Log in to your account",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 8.dp)
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Username:",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                OutlinedTextField(
                    value = state.username,
                    singleLine = true,
                    onValueChange = {
                        viewModel.updateUsername(it)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "Password:",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                OutlinedTextField(
                    value = state.password,
                    singleLine = true,
                    onValueChange = {
                        viewModel.updatePassword(it)
                    },
                    visualTransformation = if (state.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                IconButton(
                    onClick = {
                        viewModel.updatePasswordVisibility()
                    }
                ) {
                    Icon(
                        painter = if (state.isPasswordVisible) {
                            painterResource(R.drawable.ic_password_visible)
                        } else {
                            painterResource(R.drawable.ic_password_invisible)
                        },
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .padding(top = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.login)
                )
            }

            Text(
                text = stringResource(R.string.or_create_new_account),
                color = Color.Blue,
                modifier = Modifier
                    .padding(top = 32.dp)
            )

        }
    }

}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    JwtNotesTheme {
        LoginScreen() {}
    }
}

