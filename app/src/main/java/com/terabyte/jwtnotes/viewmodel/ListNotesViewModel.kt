package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}

sealed class ListNotesScreenState {

    data object Loading : ListNotesScreenState()

//    data object ErrorTokenExpired : ListNotesScreenState()

    data object ErrorNoInternet : ListNotesScreenState()

    data class Success(
        val notes: List<NoteModel>
    ) : ListNotesScreenState()
}