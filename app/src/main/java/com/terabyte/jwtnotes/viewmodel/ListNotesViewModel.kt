package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.model.NoteRequestError
import com.terabyte.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ListNotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val _stateFlowListNotesScreenState = MutableStateFlow<ListNotesScreenState>(
        ListNotesScreenState.Loading
    )
    val stateFlowListNotesScreenState = _stateFlowListNotesScreenState.asStateFlow()


    init {
        getAllNotes()
    }


    fun getAllNotes() {
        _stateFlowListNotesScreenState.value = ListNotesScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val result = getAllNotesUseCase()

            withContext(Dispatchers.Main) {
                result.onFailure { throwable ->
                    when (throwable) {
                        is NoteRequestError.TokenExpiredError -> {
                            _stateFlowListNotesScreenState.value =
                                ListNotesScreenState.ErrorTokenExpired
                        }

                        is NoteRequestError.UnknownError -> {
                            _stateFlowListNotesScreenState.value =
                                ListNotesScreenState.ErrorNoInternet
                        }
                    }
                }
                result.onSuccess {
                    _stateFlowListNotesScreenState.value = ListNotesScreenState.Success(it)
                }
            }
        }
    }
}

sealed class ListNotesScreenState {

    data object Loading : ListNotesScreenState()

    data object ErrorTokenExpired : ListNotesScreenState()

    data object ErrorNoInternet : ListNotesScreenState()

    data class Success(
        val notes: List<NoteModel>
    ) : ListNotesScreenState()
}