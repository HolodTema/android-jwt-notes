package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.note.NoteModel
import com.terabyte.domain.model.error.NoteRequestError
import com.terabyte.domain.usecase.note.DeleteNoteUseCase
import com.terabyte.domain.usecase.note.GetNoteByIdUseCase
import com.terabyte.domain.usecase.note.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlowUpdateNoteScreenState = MutableStateFlow<UpdateNoteScreenState>(
        UpdateNoteScreenState.Loading
    )
    val stateFlowUpdateNoteScreenState = _stateFlowUpdateNoteScreenState.asStateFlow()

    private val _sharedFlowSnackbarEvent = MutableSharedFlow<UpdateNoteSnackbarEvent>()
    val sharedFlowSnackbarEvent = _sharedFlowSnackbarEvent.asSharedFlow()

    init {
        val noteId: Int = savedStateHandle["noteId"] ?: error("noteId not provided")
        loadNote(noteId)
    }

    fun loadNote(noteId: Int) {
        _stateFlowUpdateNoteScreenState.value = UpdateNoteScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val resultNote = getNoteByIdUseCase(noteId)

            withContext(Dispatchers.Main) {
                if (resultNote.isSuccess) {
                    _stateFlowUpdateNoteScreenState.value =
                        UpdateNoteScreenState.NoteEditing(resultNote.getOrThrow())
                } else {
                    if (resultNote.exceptionOrNull() is NoteRequestError.TokenExpiredError) {
                        _stateFlowUpdateNoteScreenState.value =
                            UpdateNoteScreenState.ErrorTokenExpired
                    } else {
                        _sharedFlowSnackbarEvent.emit(UpdateNoteSnackbarEvent.NoInternet)
                    }
                }
            }
        }
    }

    fun updateNote() {
        val state = stateFlowUpdateNoteScreenState.value
        if (state is UpdateNoteScreenState.NoteEditing) {
            viewModelScope.launch(Dispatchers.IO) {
                val error = updateNoteUseCase(
                    state.noteModel.id,
                    state.noteModel.title,
                    state.noteModel.content
                )

                withContext(Dispatchers.Main) {
                    when (error) {
                        is NoteRequestError.TokenExpiredError -> {
                            _stateFlowUpdateNoteScreenState.value =
                                UpdateNoteScreenState.ErrorTokenExpired
                        }

                        is NoteRequestError.UnknownError -> {
                            _sharedFlowSnackbarEvent.emit(UpdateNoteSnackbarEvent.NoInternet)

                        }

                        else -> {
                            // note was updated successfully
                            _stateFlowUpdateNoteScreenState.value =
                                UpdateNoteScreenState.NoteUpdated
                        }
                    }
                }
            }
        }
    }


    fun deleteNote() {
        val state = stateFlowUpdateNoteScreenState.value
        if (state is UpdateNoteScreenState.NoteEditing) {
            val noteId = state.noteModel.id
            viewModelScope.launch(Dispatchers.IO) {
                val error = deleteNoteUseCase(noteId)

                withContext(Dispatchers.Main) {
                    when (error) {
                        is NoteRequestError.TokenExpiredError -> {
                            _stateFlowUpdateNoteScreenState.value =
                                UpdateNoteScreenState.ErrorTokenExpired
                        }

                        is NoteRequestError.UnknownError -> {
                            _sharedFlowSnackbarEvent.emit(UpdateNoteSnackbarEvent.NoInternet)
                        }

                        else -> {
                            // note deleted successfully
                            _stateFlowUpdateNoteScreenState.value =
                                UpdateNoteScreenState.NoteDeleted
                        }
                    }
                }
            }
        }
    }

    fun updateNoteTitle(title: String) {
        val state = stateFlowUpdateNoteScreenState.value
        if (state is UpdateNoteScreenState.NoteEditing) {
            val updatedNote = state.noteModel.copy(
                title = title
            )
            _stateFlowUpdateNoteScreenState.value = UpdateNoteScreenState.NoteEditing(updatedNote)
        }
    }

    fun updateNoteContent(content: String) {
        val state = stateFlowUpdateNoteScreenState.value
        if (state is UpdateNoteScreenState.NoteEditing) {
            val updatedNote = state.noteModel.copy(
                content = content
            )
            _stateFlowUpdateNoteScreenState.value = UpdateNoteScreenState.NoteEditing(updatedNote)
        }
    }
}

sealed class UpdateNoteScreenState {

    data object Loading : UpdateNoteScreenState()

    data object ErrorTokenExpired : UpdateNoteScreenState()

    data object NoteDeleted : UpdateNoteScreenState()

    data object NoteUpdated : UpdateNoteScreenState()

    data class NoteEditing(
        val noteModel: NoteModel
    ) : UpdateNoteScreenState()

}


sealed class UpdateNoteSnackbarEvent {
    data object NoInternet : UpdateNoteSnackbarEvent()
}

