package com.terabyte.domain.usecase.note

import com.terabyte.domain.model.error.NoteRequestError
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(title: String, content: String): NoteRequestError? {
        return noteRepository.createNote(title, content)
    }

}