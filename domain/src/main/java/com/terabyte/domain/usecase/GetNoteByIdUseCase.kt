package com.terabyte.domain.usecase

import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int): Result<NoteModel> {
        return noteRepository.getNoteById(noteId)
    }
}