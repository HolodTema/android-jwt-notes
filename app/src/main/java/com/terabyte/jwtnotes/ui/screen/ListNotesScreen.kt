package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
    onLogout: () -> Unit,
    onUpdateNote: (noteModel: NoteModel) -> Unit,
    onCreateNote: () -> Unit
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
                ScreenStateSuccess(
                    notes = (state as ListNotesScreenState.Success).notes,
                    onLogout = onLogout,
                    onUpdateNote = onUpdateNote,
                    onCreateNote = onCreateNote
                )
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
private fun ScreenStateSuccess(
    notes: List<NoteModel>,
    onLogout: () -> Unit,
    onUpdateNote: (NoteModel) -> Unit,
    onCreateNote: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val buttonLogout = createRef()
        val textAmountNotes = createRef()
        val lazyColumnNotes = createRef()
        val buttonAddNote = createRef()

        Button(
            onClick = onLogout,
            modifier = Modifier
                .constrainAs(buttonLogout) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        ) {
            Text(stringResource(R.string.log_out))
        }

        Text(
            text = stringResource(R.string.amount_notes, notes.size),
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .constrainAs(textAmountNotes) {
                    top.linkTo(buttonLogout.top)
                    bottom.linkTo(buttonLogout.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(buttonLogout.start)
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(lazyColumnNotes) {
                    top.linkTo(buttonLogout.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            items(notes) { noteModel ->
                NoteCard(noteModel) {
                    onUpdateNote(noteModel)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
            }
        }

        FloatingActionButton(
            onClick = onCreateNote,
            modifier = Modifier
                .constrainAs(buttonAddNote) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null
            )
        }
    }
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
                text = "Last update: ${noteModel.updatedAt.toString()}",
                fontSize = 14.sp
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenStateSuccessPreview() {
    val notes = listOf(
        NoteModel(
            id = 1,
            userId = 101,
            title = "Встреча с командой",
            content = "Обсудить планы на следующий спринт в 15:00",
            createdAt = LocalDateTime.of(2024, 1, 15, 10, 30),
            updatedAt = LocalDateTime.of(2024, 1, 15, 10, 30)
        ),
        NoteModel(
            id = 2,
            userId = 102,
            title = "Идеи для проекта",
            content = "Добавить темную тему и анимацию переходов",
            createdAt = LocalDateTime.of(2024, 1, 20, 14, 15),
            updatedAt = LocalDateTime.of(2024, 1, 21, 9, 0)
        ),
        NoteModel(
            id = 3,
            userId = 101,
            title = "Список покупок",
            content = "Молоко, яйца, хлеб, яблоки, кофе",
            createdAt = LocalDateTime.of(2024, 2, 1, 8, 0),
            updatedAt = LocalDateTime.of(2024, 2, 1, 8, 0)
        ),
        NoteModel(
            id = 4,
            userId = 103,
            title = "Важные дедлайны",
            content = "До 25 марта сдать отчет, до 30 марта - презентацию",
            createdAt = LocalDateTime.of(2024, 2, 10, 11, 45),
            updatedAt = LocalDateTime.of(2024, 2, 12, 16, 20)
        ),
        NoteModel(
            id = 5,
            userId = 102,
            title = "Рефлексия",
            content = "Что получилось на этой неделе, а над чем еще работать",
            createdAt = LocalDateTime.of(2024, 2, 18, 19, 0),
            updatedAt = LocalDateTime.of(2024, 2, 19, 10, 30)
        )
    )

    JwtNotesTheme {
        ScreenStateSuccess(
            notes = notes,
            onLogout = {

            },
            onCreateNote = {

            },
            onUpdateNote = {

            }
        )
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


