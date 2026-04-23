package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.bdui.ColumnComponent
import com.terabyte.domain.model.bdui.Component
import com.terabyte.domain.model.bdui.RowComponent
import com.terabyte.domain.model.bdui.TextComponent
import com.terabyte.domain.model.bdui.TextFieldComponent
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
import kotlin.collections.forEach


@HiltViewModel
class CreateNoteBdUiViewModel @Inject constructor(
    private val getCreateNoteScreenBdUiUseCase: GetCreateNoteScreenBdUiUseCase,
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel() {
    private val _stateFlowScreenState =
        MutableStateFlow<CreateNoteBdUiScreenState>(CreateNoteBdUiScreenState.Loading)
    val stateFlowScreenState = _stateFlowScreenState.asStateFlow()

    private val _stateFlowMapTextFieldStates = MutableStateFlow<Map<String, String>>(emptyMap())
    val stateFlowMapTextFieldStates = _stateFlowMapTextFieldStates.asStateFlow()

    init {
        loadBdUi()
    }

    fun loadBdUi() {
        _stateFlowScreenState.value = CreateNoteBdUiScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val result = getCreateNoteScreenBdUiUseCase()

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    _stateFlowScreenState.value = CreateNoteBdUiScreenState.Success(result.getOrThrow())
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


    // sync mapTextFieldStates with actual list of textFieldIds from component
    //
    // existing ids from old mapTextFieldStates are paired to their old values
    // new ids are paired with ""
    // not-existing ids are deleted
    //
    // we need to do such sync every time we get new BDUI json
    fun syncTextFieldsWithComponent(component: Component) {
        val ids = extractTextFieldIds(component)
        _stateFlowMapTextFieldStates.update { oldMap ->
            val newMap = mutableMapOf<String, String>()
            ids.forEach { id ->
                newMap[id] = oldMap[id] ?: ""
            }
            newMap
        }
    }


    // recursive traversal of the component and its child components to get all the TextFieldComponent.id
    private fun extractTextFieldIds(component: Component): List<String> {
        val ids = mutableListOf<String>()

        fun traverse(comp: Component) {
            when (comp) {
                is TextFieldComponent -> {
                    ids.add(comp.id)
                }

                is ColumnComponent -> {
                    comp.children.forEach {
                        traverse(it)
                    }
                }

                is RowComponent -> {
                    comp.children.forEach {
                        traverse(it)
                    }
                }

                else -> {
                    // do nothing, because only Row and Column can have
                    // child components inside themselves
                }
            }
        }
        
        traverse(component)
        return ids
    }


    fun updateTextField(textFieldId: String, value: String) {
        _stateFlowMapTextFieldStates.update { oldMap ->
            oldMap.toMutableMap().apply {
                put(textFieldId, value)
            }
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
    ) : CreateNoteBdUiScreenState()
}

