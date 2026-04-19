package com.terabyte.domain.usecase.note

import com.terabyte.domain.model.error.NoteRequestError
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int, title: String, content: String): NoteRequestError? {
        return noteRepository.updateNote(noteId, title, content)
    }

}

