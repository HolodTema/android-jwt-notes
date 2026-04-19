package com.terabyte.jwtnotes.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.viewmodel.RegistrationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onRegistrationSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.stateFlowRegisterScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(state.registrationSuccess) {
        if (state.registrationSuccess) {
            onRegistrationSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.create_new_account),
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
                isError = state.isErrorUsernameValidation,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.isErrorUsernameValidation) {
                Text(
                    text = stringResource(R.string.invalid_username_format),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Поле Email
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text(stringResource(R.string.email)) },
                isError = state.isErrorEmailValidation,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            if (state.isErrorEmailValidation) {
                Text(
                    text = stringResource(R.string.invalid_email_format),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Поле Password с иконкой видимости
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text(stringResource(R.string.password)) },
                isError = state.isErrorPasswordValidation,
                singleLine = true,
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
            if (state.isErrorPasswordValidation) {
                Text(
                    text = stringResource(R.string.invalid_password_format),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Общая ошибка регистрации
            if (state.isErrorUnableToRegister || state.isErrorUsernameBusy) {
                Text(
                    text = if (state.isErrorUsernameBusy) {
                        stringResource(R.string.error_username_busy)
                    } else {
                        stringResource(R.string.error_unable_to_register)
                    },
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }

            // Кнопка регистрации или прогресс
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
                        onClick = { viewModel.register() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.register))
                    }
                }
            }

            // Ссылка на вход
            Text(
                text = stringResource(R.string.or_log_in),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .clickable { onNavigateToLogin() }
            )
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RegistrationScreenPreview() {
    JwtNotesTheme {
        RegistrationScreen(
            onRegistrationSuccess = {},
            onNavigateToLogin = {}
        )
    }
}
