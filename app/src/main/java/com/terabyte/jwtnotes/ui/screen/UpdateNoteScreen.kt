package com.terabyte.jwtnotes.ui.screen

import android.R.attr.duration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.terabyte.domain.model.NoteModel
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.UpdateNoteScreenState
import com.terabyte.jwtnotes.viewmodel.UpdateNoteViewModel


@Composable
fun UpdateNoteScreen(
    viewModel: UpdateNoteViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit,
    onNoteUpdated: () -> Unit
) {
    val state by viewModel.stateFlowUpdateNoteScreenState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }


    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.edit_note),
                fontSize = 18.sp
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is UpdateNoteScreenState.Loading -> {
                    ScreenStateLoading()
                }

                UpdateNoteScreenState.ErrorNoInternet -> {
                    val result = snackbarHostState.showSnackbar(
                        message = stringResource(R.string.no_internet),
                        actionLabel = stringResource(R.string.try_again),
                        duration = SnackbarDuration.Long
                    )
                }

                UpdateNoteScreenState.ErrorTokenExpired -> {
                    onTokenExpired()
                }

                is UpdateNoteScreenState.NoteEditing -> {
                    ScreenStateNoteEditing((state as UpdateNoteScreenState.NoteEditing).noteModel.)
                }
            }
        }
    }
}

@Composable
private fun ScreenStateLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ScreenStateNoteEditing(
    stateNoteModel: MutableState<NoteModel>,
    onNoteUpdateClicked: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = stateNoteModel.value.title,
            onValueChange = {
                stateNoteModel.value = stateNoteModel.value.copy(
                    title = it
                )
            },
            label = {
                Text(stringResource(R.string.note_title))
            },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = stateNoteModel.value.content,
            onValueChange = {
                stateNoteModel.value = stateNoteModel.value.copy(
                    content = it
                )
            },
            label = {
                Text(stringResource(R.string.note_text))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Button(
            onClick = onNoteUpdateClicked
        ) {
            Text(
                text = stringResource(R.string.save_note)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScreenStateNoteEditingPreview() {
    JwtNotesTheme {
        ScreenStateNoteEditing()
    }
}


