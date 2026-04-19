package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    private val _stateFlowUpdateNoteScreenState = MutableStateFlow<UpdateNoteScreenState>(
        UpdateNoteScreenState.Loading
    )
    val stateFlowUpdateNoteScreenState = _stateFlowUpdateNoteScreenState.asStateFlow()

    init {

    }

    fun updateNoteTitle() {

    }

    fun updateNoteContent(content: String) {
        _stateFlowUpdateNoteScreenState.update {

        }
    }

    fun saveUpdatedNote() {

    }
}

sealed class UpdateNoteScreenState {

    data object Loading : UpdateNoteScreenState()

    data object ErrorTokenExpired : UpdateNoteScreenState()

    data object ErrorNoInternet : UpdateNoteScreenState()

    data class NoteEditing(
        val noteModel: NoteModel
    ) : UpdateNoteScreenState()

}

