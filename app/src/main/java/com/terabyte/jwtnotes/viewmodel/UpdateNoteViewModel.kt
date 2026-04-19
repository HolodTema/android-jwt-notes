package com.terabyte.jwtnotes.viewmodel

import androidx.lifecycle.ViewModel
import com.terabyte.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase
): ViewModel() {


    fun updateNote() {

    }
}

data class UpdateNoteScreenState(
    val
)