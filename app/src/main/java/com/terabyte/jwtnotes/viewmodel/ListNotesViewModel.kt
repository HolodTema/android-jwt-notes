package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.model.NoteRequestError
import com.terabyte.domain.model.UserDetailsModel
import com.terabyte.domain.model.UserDetailsRequestError
import com.terabyte.domain.usecase.GetAllNotesUseCase
import com.terabyte.domain.usecase.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ListNotesViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val _stateFlowListNotesScreenState = MutableStateFlow<ListNotesScreenState>(
        ListNotesScreenState.Loading
    )
    val stateFlowListNotesScreenState = _stateFlowListNotesScreenState.asStateFlow()


    init {
        getAllNotesAndUserDetails()
    }


    fun getAllNotesAndUserDetails() {
        _stateFlowListNotesScreenState.value = ListNotesScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val deferredNotes = async {
                getAllNotesUseCase()
            }
            val deferredUserDetails = async {
                getUserDetailsUseCase()
            }

            val resultNotes = deferredNotes.await()
            val resultUserDetails = deferredUserDetails.await()

            withContext(Dispatchers.Main) {
                when {
                    (resultNotes.exceptionOrNull() is NoteRequestError.TokenExpiredError ||
                            resultUserDetails.exceptionOrNull() is UserDetailsRequestError.TokenExpiredError) -> {
                        _stateFlowListNotesScreenState.value =
                            ListNotesScreenState.ErrorTokenExpired
                    }

                    (resultNotes.isFailure || resultUserDetails.isFailure) -> {
                        _stateFlowListNotesScreenState.value =
                            ListNotesScreenState.ErrorNoInternet
                    }

                    else -> {
                        val notes = resultNotes.getOrThrow()
                        val userDetails = resultUserDetails.getOrThrow()
                        _stateFlowListNotesScreenState.value =
                            ListNotesScreenState.Success(userDetails, notes)

                    }
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
        val userDetailsModel: UserDetailsModel,
        val notes: List<NoteModel>
    ) : ListNotesScreenState()
}