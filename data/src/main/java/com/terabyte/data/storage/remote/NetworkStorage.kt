package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.auth.LoginRequestJson
import com.terabyte.data.storage.remote.model.auth.LoginResponseJson
import com.terabyte.data.storage.remote.model.note.NoteJson
import com.terabyte.data.storage.remote.model.note.NoteRequestJson
import com.terabyte.data.storage.remote.model.auth.RegisterRequestJson
import com.terabyte.data.storage.remote.model.user.UserDetailsJson
import com.terabyte.domain.model.error.NoteRequestError

interface NetworkStorage {

    suspend fun login(loginRequestJson: LoginRequestJson): Result<LoginResponseJson>

    suspend fun register(registerRequestJson: RegisterRequestJson): Result<LoginResponseJson>

    suspend fun getAllNotes(): Result<List<NoteJson>>

    suspend fun getUserDetails(): Result<UserDetailsJson>

    suspend fun createNote(noteRequestJson: NoteRequestJson): NoteRequestError?

    suspend fun updateNote(noteId: Int, noteRequestJson: NoteRequestJson): NoteRequestError?

    suspend fun deleteNote(noteId: Int): NoteRequestError?

    suspend fun getNoteById(noteId: Int): Result<NoteJson>

}