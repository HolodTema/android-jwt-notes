package com.terabyte.domain.usecase

import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(): Result<List<NoteModel>> {
        return noteRepository.getAllNotes()
    }

}