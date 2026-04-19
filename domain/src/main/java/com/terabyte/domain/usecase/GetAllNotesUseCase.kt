package com.terabyte.domain.usecase

import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(): Result<List<NoteModel>> {
        return noteRepository.getAllNotes().map { notes ->
            notes.sortedWith { note1, note2 ->
                // obj2.compareTo(obj1) to get descending sort
                note2.updatedAt.compareTo(note1.updatedAt)
            }
        }
    }

}