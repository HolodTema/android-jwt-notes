package com.terabyte.jwtnotes.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.CreateNoteViewModel


@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit,
    onNoteCreated: () -> Unit
) {
    val state by viewModel.stateFlowCreateNoteScreenState.collectAsStateWithLifecycle()

    BackHandler(enabled = true) {
        viewModel.createNote()
    }

    LaunchedEffect(state.isTokenExpired) {
        if (state.isTokenExpired) {
            onTokenExpired()
        }
    }

    LaunchedEffect(state.isCreated) {
        if (state.isCreated) {
            onNoteCreated()
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarTextNoInternet = stringResource(R.string.no_internet)
    val snackbarActionLabel = stringResource(R.string.try_again)
    LaunchedEffect(state.showSnackBarNoInternet) {
        if (state.showSnackBarNoInternet) {
            val result = snackbarHostState.showSnackbar(
                message = snackbarTextNoInternet,
                actionLabel = snackbarActionLabel,
                duration = SnackbarDuration.Long
            )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    // user clicked label Try again on Snackbar
                    viewModel.onSnackbarNoInternetShown()
                    viewModel.createNote()
                }

                SnackbarResult.Dismissed -> {
                    viewModel.onSnackbarNoInternetShown()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.create_note),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = viewModel::updateTitle,
                label = {
                    Text(stringResource(R.string.note_title))
                },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = state.content,
                onValueChange = viewModel::updateContent,
                label = {
                    Text(stringResource(R.string.note_text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = {
                    viewModel.createNote()
                }
            ) {
                Text(
                    text = stringResource(R.string.save_note)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateNoteScreenPreview() {
    JwtNotesTheme {
        CreateNoteScreen(
            onTokenExpired = {

            },
            onNoteCreated = {

            }
        )
    }
}