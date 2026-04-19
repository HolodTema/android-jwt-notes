package com.terabyte.domain.usecase

import com.terabyte.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke() {

    }

}