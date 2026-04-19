package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.domain.model.NoteModel
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.UpdateNoteScreenState
import com.terabyte.jwtnotes.viewmodel.UpdateNoteSnackbarEvent
import com.terabyte.jwtnotes.viewmodel.UpdateNoteViewModel
import java.time.LocalDateTime


@Composable
fun UpdateNoteScreen(
    viewModel: UpdateNoteViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit,
    onNoteEditingFinished: () -> Unit
) {
    val state by viewModel.stateFlowUpdateNoteScreenState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val strNoInternet = stringResource(R.string.no_internet)
    LaunchedEffect(Unit) {
        viewModel.sharedFlowSnackbarEvent.collect { event ->
            when (event) {
                is UpdateNoteSnackbarEvent.NoInternet -> {
                    val result = snackbarHostState.showSnackbar(
                        message = strNoInternet,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }


    Scaffold(
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

                UpdateNoteScreenState.ErrorTokenExpired -> {
                    onTokenExpired()
                }

                is UpdateNoteScreenState.NoteEditing -> {
                    val noteModel = (state as UpdateNoteScreenState.NoteEditing).noteModel
                    ScreenStateNoteEditing(
                        noteModel = noteModel,
                        onTitleChanged = viewModel::updateNoteTitle,
                        onContentChanged = viewModel::updateNoteContent,
                        onDeleteNoteClicked = viewModel::deleteNote,
                        onUpdateNoteClicked = viewModel::updateNote
                    )
                }

                is UpdateNoteScreenState.NoteUpdated, UpdateNoteScreenState.NoteDeleted -> {
                    onNoteEditingFinished()
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
    noteModel: NoteModel,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onUpdateNoteClicked: () -> Unit,
    onDeleteNoteClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.edit_note),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            IconButton(
                onClick = onDeleteNoteClicked
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            OutlinedTextField(
                value = noteModel.title,
                onValueChange = onTitleChanged,
                label = {
                    Text(stringResource(R.string.note_title))
                },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = noteModel.content,
                onValueChange = onContentChanged,
                label = {
                    Text(stringResource(R.string.note_text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = onUpdateNoteClicked
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
private fun ScreenStateNoteEditingPreview() {
    val noteModel = NoteModel(
        id = 0,
        userId = 0,
        title = "Note title",
        content = "Some text of the note.",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
    JwtNotesTheme {
        ScreenStateNoteEditing(
            noteModel = noteModel,
            onTitleChanged = {},
            onContentChanged = {},
            onUpdateNoteClicked = {},
            onDeleteNoteClicked = {}
        )
    }
}



