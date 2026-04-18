package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.domain.model.NoteModel
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.ListNotesScreenState
import com.terabyte.jwtnotes.viewmodel.ListNotesViewModel
import java.time.LocalDateTime


@Composable
fun ListNotesScreen(
    viewModel: ListNotesViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.stateFlowListNotesScreenState.collectAsStateWithLifecycle()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            is ListNotesScreenState.Loading -> {
                ScreenStateLoading()
            }

            is ListNotesScreenState.ErrorNoInternet -> {
                ScreenStateErrorNoInternet {
                    viewModel.getAllNotes()
                }
            }

            is ListNotesScreenState.ErrorTokenExpired -> {
                onTokenExpired()
            }

            is ListNotesScreenState.Success -> {
                ScreenStateSuccess()
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
private fun ScreenStateErrorNoInternet(
    onTryAgain: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            fontSize = 20.sp
        )
        Text(
            text = stringResource(R.string.check_internet_connection)
        )
        Button(
            onClick = onTryAgain,
            modifier = Modifier
                .padding(top = 32.dp)
        ) {
            Text(
                text = stringResource(R.string.try_again)
            )
        }
    }
}

@Composable
private fun ScreenStateSuccess() {

}


@Composable
private fun NoteCard(
    noteModel: NoteModel,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = noteModel.title,
                fontSize = 18.sp
            )
            Text(
                text = noteModel.content,
                fontSize = 14.sp
            )
            Text(
                text = "Last update: ${noteModel.updatedAt.toString()}"
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenStateLoadingPreview() {
    JwtNotesTheme {
        ScreenStateLoading()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenStateErrorNoInternetPreview() {
    JwtNotesTheme {
        ScreenStateErrorNoInternet { }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteCardPreview() {
    val noteModel = NoteModel(
        id = 0,
        userId = 0,
        title = "Note title",
        content = "Some text of the note",
        createdAt = LocalDateTime.of(2025, 1, 1, 21, 0, 0),
        updatedAt = LocalDateTime.now()
    )

    JwtNotesTheme {
        NoteCard(noteModel) { }
    }
}


