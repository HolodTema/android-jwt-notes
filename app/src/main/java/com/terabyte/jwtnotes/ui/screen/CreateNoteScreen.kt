package com.terabyte.jwtnotes.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.viewmodel.CreateNoteScreenState
import com.terabyte.jwtnotes.viewmodel.CreateNoteViewModel

@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel = hiltViewModel(),
    onTokenExpired: () -> Unit
) {
    val state by viewModel.stateFlowCreateNoteScreenState.collectAsStateWithLifecycle()

    when (state) {
        is CreateNoteScreenState.CreateNote -> {

        }
        CreateNoteScreenState.ErrorNoInternet -> {

        }
        CreateNoteScreenState.ErrorTokenExpired -> {
            onTokenExpired()
        }
    }
}

private fun