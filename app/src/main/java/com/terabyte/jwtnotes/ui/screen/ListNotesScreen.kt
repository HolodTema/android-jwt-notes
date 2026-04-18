package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.ListNotesScreenState
import com.terabyte.jwtnotes.viewmodel.ListNotesViewModel


@Composable
fun ListNotesScreen(
    viewModel: ListNotesViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val state by viewModel.stateFlowListNotesScreenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when(state) {
                is ListNotesScreenState.Loading -> {
                    ScreenStateLoading()
                }
                is ListNotesScreenState.ErrorNoInternet -> {
                    ScreenStateErrorNoInternet()
                }
                is ListNotesScreenState.Success -> {
                    ScreenStateSuccess()
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
private fun ScreenStateErrorNoInternet() {

}

@Composable
private fun ScreenStateSuccess() {

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenStateLoadingPreview() {
    JwtNotesTheme {
        ScreenStateLoading()
    }
}

