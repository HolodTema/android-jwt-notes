package com.terabyte.domain.usecase.note

import com.terabyte.domain.model.error.NoteRequestError
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int): NoteRequestError? {
        return noteRepository.deleteNote(noteId)
    }

}
