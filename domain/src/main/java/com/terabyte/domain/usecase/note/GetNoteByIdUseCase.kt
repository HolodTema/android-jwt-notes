package com.terabyte.domain.usecase.note

import com.terabyte.domain.model.note.NoteModel
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int): Result<NoteModel> {
        return noteRepository.getNoteById(noteId)
    }
}