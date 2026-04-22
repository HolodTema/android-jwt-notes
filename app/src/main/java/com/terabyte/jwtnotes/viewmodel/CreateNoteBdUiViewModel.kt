package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.bdui.Component
import com.terabyte.domain.model.error.BdUiRequestError
import com.terabyte.domain.model.error.NoteRequestError
import com.terabyte.domain.usecase.bdui.GetCreateNoteScreenBdUiUseCase
import com.terabyte.domain.usecase.note.CreateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CreateNoteBdUiViewModel @Inject constructor(
    private val getCreateNoteScreenBdUiUseCase: GetCreateNoteScreenBdUiUseCase,
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel() {
    private val _stateFlowScreenState =
        MutableStateFlow<CreateNoteBdUiScreenState>(CreateNoteBdUiScreenState.Loading)
    val stateFlowScreenState = _stateFlowScreenState.asStateFlow()

    init {
        loadBdUi()
    }

    fun loadBdUi() {
        _stateFlowScreenState.value = CreateNoteBdUiScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val result = getCreateNoteScreenBdUiUseCase()

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    _stateFlowScreenState.value = CreateNoteBdUiScreenState.Success(result.getOrThrow(), mutableMapOf())
                }
                else {
                    val error = result.exceptionOrNull()

                    when (error) {
                        is BdUiRequestError.TokenExpiredError -> {
                            _stateFlowScreenState.value = CreateNoteBdUiScreenState.ErrorTokenExpired
                        }
                        is BdUiRequestError.UnknownError -> {
                            _stateFlowScreenState.value = CreateNoteBdUiScreenState.ErrorNoInternet
                        }
                    }
                }
            }
        }
    }


    fun createNote() {
        if (stateFlowScreenState.value is CreateNoteBdUiScreenState.Success) {
            val state = stateFlowScreenState.value as CreateNoteBdUiScreenState.Success
            val title = ""
            val content = ""

            viewModelScope.launch(Dispatchers.IO) {
                val noteRequestError = createNoteUseCase(title, content)

                withContext(Dispatchers.Main) {
                    when (noteRequestError) {
                        is NoteRequestError.UnknownError -> {
                            _stateFlowScreenState.value = CreateNoteBdUiScreenState.ErrorNoInternet
                        }
                        is NoteRequestError.TokenExpiredError -> {
                            _stateFlowScreenState.value = CreateNoteBdUiScreenState.ErrorTokenExpired
                        }
                        else -> {
                            _stateFlowScreenState.value = CreateNoteBdUiScreenState.NoteCreated
                        }
                    }
                }
            }
        }
    }


    fun onTextFieldValueChange(
        textFieldId: String,
        value: String
    ) {
        val state = _stateFlowScreenState.value
        if (state is CreateNoteBdUiScreenState.Success) {
            val mapStates = state.mapTextFieldStates
            mapStates[textFieldId] = value

            _stateFlowScreenState.value = CreateNoteBdUiScreenState.Success(
                component = state.component,
                mapTextFieldStates = mapStates
            )
        }
    }

}


sealed class CreateNoteBdUiScreenState {

    data object Loading : CreateNoteBdUiScreenState()

    data object ErrorTokenExpired : CreateNoteBdUiScreenState()

    data object ErrorNoInternet : CreateNoteBdUiScreenState()

    data object NoteCreated : CreateNoteBdUiScreenState()

    data class Success(
        val component: Component,
        val mapTextFieldStates: MutableMap<String, String>
    ) : CreateNoteBdUiScreenState()
}

