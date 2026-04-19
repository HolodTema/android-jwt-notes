package com.terabyte.domain.usecase

import com.terabyte.domain.model.NoteRequestError
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int): NoteRequestError? {
        return noteRepository.deleteNote(noteId)
    }

}
