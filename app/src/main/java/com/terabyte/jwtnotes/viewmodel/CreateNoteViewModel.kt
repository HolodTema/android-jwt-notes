package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import com.terabyte.domain.usecase.CreateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel() {

    private val _stateFlowCreateNoteScreenState = MutableStateFlow<CreateNoteScreenState>(
        CreateNoteScreenState.CreateNote()
    )
    val stateFlowCreateNoteScreenState = _stateFlowCreateNoteScreenState.asStateFlow()

    fun createNote() {

    }
}

sealed class CreateNoteScreenState {

    data class CreateNote(
        val title: String = "",
        val content: String = ""
    ) : CreateNoteScreenState()

    data object ErrorNoInternet : CreateNoteScreenState()

    data object ErrorTokenExpired : CreateNoteScreenState()
}