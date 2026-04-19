package com.terabyte.jwtnotes.ui.screen

import androidx.compose.ui.tooling.preview.Preview
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import java.time.LocalDateTime
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.domain.model.note.NoteModel
import com.terabyte.domain.model.user.UserDetailsModel
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.viewmodel.ListNotesScreenState
import com.terabyte.jwtnotes.viewmodel.ListNotesViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNotesScreen(
    viewModel: ListNotesViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit = {},
    onLogout: () -> Unit = {},
    onUpdateNote: (NoteModel) -> Unit = {},
    onCreateNote: () -> Unit = {}
) {
    val state by viewModel.stateFlowListNotesScreenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_notes),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateNote,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_note))
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (state) {
                is ListNotesScreenState.Loading -> {
                    ScreenStateLoading()
                }
                is ListNotesScreenState.ErrorNoInternet -> {
                    ScreenStateErrorNoInternet {
                        viewModel.getAllNotesAndUserDetails()
                    }
                }
                is ListNotesScreenState.ErrorTokenExpired -> {
                    onTokenExpired()
                }
                is ListNotesScreenState.Success -> {
                    ScreenStateSuccess(
                        userDetailsModel = (state as ListNotesScreenState.Success).userDetailsModel,
                        notes = (state as ListNotesScreenState.Success).notes,
                        onLogout = { viewModel.logout() },
                        onUpdateNote = onUpdateNote
                    )
                }
                is ListNotesScreenState.Logout -> {
                    onLogout()
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
private fun ScreenStateErrorNoInternet(onTryAgain: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = stringResource(R.string.check_internet_connection),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Button(
            onClick = onTryAgain,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(stringResource(R.string.try_again))
        }
    }
}

@Composable
private fun ScreenStateSuccess(
    userDetailsModel: UserDetailsModel,
    notes: List<NoteModel>,
    onLogout: () -> Unit,
    onUpdateNote: (NoteModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Верхняя панель с информацией о пользователе и кнопкой выхода
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = userDetailsModel.username,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = userDetailsModel.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(onClick = onLogout) {
                Text(stringResource(R.string.log_out))
            }
        }

        // Счётчик заметок
        Text(
            text = stringResource(R.string.amount_notes, notes.size),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Список заметок
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = notes,
                key = { it.id }
            ) { note ->
                NoteCard(
                    noteModel = note,
                    onClick = { onUpdateNote(note) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun NoteCard(
    noteModel: NoteModel,
    onClick: () -> Unit
) {
    // Форматирование даты с кэшированием
    val formattedDate = remember(noteModel.updatedAt) {
        noteModel.updatedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }

    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = noteModel.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = noteModel.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Last update: $formattedDate",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


