package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.NoteRequestError
import com.terabyte.domain.usecase.CreateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel() {

    private val _stateFlowCreateNoteScreenState = MutableStateFlow(CreateNoteScreenState())
    val stateFlowCreateNoteScreenState = _stateFlowCreateNoteScreenState.asStateFlow()

    fun createNote() {
        val title = stateFlowCreateNoteScreenState.value.title
        val content = stateFlowCreateNoteScreenState.value.content
        viewModelScope.launch(Dispatchers.IO) {
            val noteRequestError = createNoteUseCase(title, content)
            withContext(Dispatchers.Main) {
                when (noteRequestError) {
                    is NoteRequestError.UnknownError -> {
                        _stateFlowCreateNoteScreenState.update {
                            it.copy(
                                showSnackBarNoInternet = true
                            )
                        }
                    }
                    is NoteRequestError.TokenExpiredError -> {
                        _stateFlowCreateNoteScreenState.update {
                            it.copy(
                                isTokenExpired = true
                            )
                        }
                    }
                    else -> {
                        _stateFlowCreateNoteScreenState.update {
                            it.copy(
                                isCreated = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _stateFlowCreateNoteScreenState.update {
            it.copy(
                title = title
            )
        }
    }

    fun updateContent(content: String) {
        _stateFlowCreateNoteScreenState.update {
            it.copy(
                content = content
            )
        }
    }

    fun onSnackbarNoInternetShown() {
        _stateFlowCreateNoteScreenState.update {
            it.copy(
                showSnackBarNoInternet = false
            )
        }
    }
}

data class CreateNoteScreenState(
    val title: String = "",
    val content: String = "",
    val isCreated: Boolean = false,
    val showSnackBarNoInternet: Boolean = false,
    val isTokenExpired: Boolean = false
)


