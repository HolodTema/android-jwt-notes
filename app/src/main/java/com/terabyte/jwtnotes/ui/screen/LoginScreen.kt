package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegistration: () -> Unit = {}
) {
    val state by viewModel.stateFlowLoginScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.log_in_to_account),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            // Поле Username
            OutlinedTextField(
                value = state.username,
                onValueChange = { viewModel.updateUsername(it) },
                label = { Text(stringResource(R.string.username)) },
                singleLine = true,
                isError = state.isLoginError,
                modifier = Modifier.fillMaxWidth()
            )

            // Поле Password с иконкой видимости
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                isError = state.isLoginError,
                visualTransformation = if (state.isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { viewModel.updatePasswordVisibility() }) {
                        Icon(
                            painter = if (state.isPasswordVisible) {
                                painterResource(R.drawable.ic_password_invisible)
                            } else {
                                painterResource(R.drawable.ic_password_visible)
                            },
                            contentDescription = if (state.isPasswordVisible) {
                                stringResource(R.string.hide_password)
                            } else {
                                stringResource(R.string.show_password)
                            }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            // Ошибка логина
            if (state.isLoginError) {
                Text(
                    text = stringResource(R.string.login_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }

            // Кнопка логина или прогресс
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(72.dp)
                    .padding(top = 24.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.login))
                    }
                }
            }

            // Ссылка на регистрацию
            Text(
                text = stringResource(R.string.or_create_new_account),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .clickable { onNavigateToRegistration() }
            )
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    JwtNotesTheme {
        LoginScreen(
            onLoginSuccess = {},
            onNavigateToRegistration = {}
        )
    }
}

