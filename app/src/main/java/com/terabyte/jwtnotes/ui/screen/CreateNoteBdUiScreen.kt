package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.viewmodel.CreateNoteBdUiScreenState
import com.terabyte.jwtnotes.viewmodel.CreateNoteBdUiViewModel


@Composable
fun CreateNoteBdUiScreen(
    viewModel: CreateNoteBdUiViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit,
    onNoteCreated: () -> Unit
    ) {
    val state by viewModel.stateFlowScreenState.collectAsStateWithLifecycle()

    when (state) {
        is CreateNoteBdUiScreenState.Loading -> {
            ScreenStateLoading()
        }
        is CreateNoteBdUiScreenState.ErrorNoInternet -> {
            ScreenStateErrorNoInternet {
                viewModel.loadBdUi()
            }
        }
        is CreateNoteBdUiScreenState.ErrorTokenExpired -> {
            onTokenExpired()
        }
        is CreateNoteBdUiScreenState.NoteCreated -> {
            onNoteCreated()
        }
        is CreateNoteBdUiScreenState.Success -> {

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