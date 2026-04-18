package com.terabyte.domain.repository

import com.terabyte.domain.model.NoteModel

interface NoteRepository {

    suspend fun getAllNotes(): Result<List<NoteModel>>

}