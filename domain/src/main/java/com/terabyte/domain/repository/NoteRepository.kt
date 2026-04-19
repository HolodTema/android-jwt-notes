package com.terabyte.domain.repository

import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.model.NoteRequestError

interface NoteRepository {

    suspend fun getAllNotes(): Result<List<NoteModel>>

    suspend fun createNote(title: String, content: String): NoteRequestError?

    suspend fun updateNote(noteId: Int, title: String, content: String): NoteRequestError?

    suspend fun deleteNote(noteId: Int): NoteRequestError?

    suspend fun getNoteById(noteId: Int): Result<NoteModel>

}