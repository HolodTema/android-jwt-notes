package com.terabyte.jwtnotes.ui.screen

import com.terabyte.domain.model.note.NoteModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.viewmodel.UpdateNoteScreenState
import com.terabyte.jwtnotes.viewmodel.UpdateNoteSnackbarEvent
import com.terabyte.jwtnotes.viewmodel.UpdateNoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteScreen(
    viewModel: UpdateNoteViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit,
    onNoteEditingFinished: () -> Unit
) {
    val state by viewModel.stateFlowUpdateNoteScreenState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val strNoInternet = stringResource(R.string.no_internet)

    // Сбор событий для снекбара
    LaunchedEffect(Unit) {
        viewModel.sharedFlowSnackbarEvent.collect { event ->
            when (event) {
                is UpdateNoteSnackbarEvent.NoInternet -> {
                    snackbarHostState.showSnackbar(
                        message = strNoInternet,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit_note),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    // Кнопка удаления заметки (только если состояние NoteEditing)
                    if (state is UpdateNoteScreenState.NoteEditing) {
                        IconButton(
                            onClick = { viewModel.deleteNote() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_note),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                is UpdateNoteScreenState.ErrorTokenExpired -> {
                    onTokenExpired()
                }
                is UpdateNoteScreenState.NoteEditing -> {
                    val noteModel = (state as UpdateNoteScreenState.NoteEditing).noteModel
                    ScreenStateNoteEditing(
                        noteModel = noteModel,
                        onTitleChanged = viewModel::updateNoteTitle,
                        onContentChanged = viewModel::updateNoteContent,
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
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ScreenStateNoteEditing(
    noteModel: NoteModel,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onUpdateNoteClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        // Контент центрируется по вертикали
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
                label = { Text(stringResource(R.string.note_title)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = noteModel.content,
                onValueChange = onContentChanged,
                label = { Text(stringResource(R.string.note_text)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Button(
                onClick = onUpdateNoteClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(stringResource(R.string.save_note))
            }
        }
    }
}


