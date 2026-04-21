package com.terabyte.data.storage.remote

import android.R
import com.terabyte.data.storage.remote.model.auth.LoginRequestJson
import com.terabyte.data.storage.remote.model.auth.LoginResponseJson
import com.terabyte.data.storage.remote.model.note.NoteJson
import com.terabyte.data.storage.remote.model.note.NoteRequestJson
import com.terabyte.data.storage.remote.model.auth.RegisterRequestJson
import com.terabyte.data.storage.remote.model.bdui.ComponentJson
import com.terabyte.data.storage.remote.model.user.UserDetailsJson
import com.terabyte.domain.model.error.BdUiRequestError
import com.terabyte.domain.model.error.NoteRequestError
import com.terabyte.domain.model.error.RegistrationError
import com.terabyte.domain.model.error.UserDetailsRequestError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStorageImpl @Inject constructor(
    private val retrofitService: RetrofitService
) : NetworkStorage {

    override suspend fun login(loginRequestJson: LoginRequestJson): Result<LoginResponseJson> {
        val response = retrofitService.login(loginRequestJson)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(Exception(response.code().toString()))
            } else {
                Result.success(body)
            }
        } else {
            Result.failure(Exception(response.code().toString()))
        }
    }

    override suspend fun register(registerRequestJson: RegisterRequestJson): Result<LoginResponseJson> {
        val response = retrofitService.register(registerRequestJson)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(RegistrationError.UnknownError())
            } else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 409) {
                Result.failure(RegistrationError.UsernameBusy())
            }
            else {
                Result.failure(RegistrationError.UnknownError())
            }
        }
    }

    override suspend fun getAllNotes(): Result<List<NoteJson>> {
        val response = retrofitService.getAllNotes()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(NoteRequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(NoteRequestError.TokenExpiredError())
            }
            else {
                Result.failure(NoteRequestError.UnknownError())
            }
        }
    }

    override suspend fun getUserDetails(): Result<UserDetailsJson> {
        val response = retrofitService.getUserDetails()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(UserDetailsRequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(UserDetailsRequestError.TokenExpiredError())
            }
            else {
                Result.failure(UserDetailsRequestError.UnknownError())
            }
        }
    }

    override suspend fun createNote(noteRequestJson: NoteRequestJson): NoteRequestError? {
        val response = retrofitService.createNote(noteRequestJson)
        return if (response.isSuccessful) {
            null
        }
        else {
            if (response.code() == 401) {
                NoteRequestError.TokenExpiredError()
            }
            else {
                NoteRequestError.UnknownError()
            }
        }
    }

    override suspend fun updateNote(
        noteId: Int,
        noteRequestJson: NoteRequestJson
    ): NoteRequestError? {
        val response = retrofitService.updateNote(noteId, noteRequestJson)
        return if (response.isSuccessful) {
            null
        }
        else {
            if (response.code() == 401) {
                NoteRequestError.TokenExpiredError()
            }
            else {
                NoteRequestError.UnknownError()
            }
        }
    }

    override suspend fun deleteNote(noteId: Int): NoteRequestError? {
        val response = retrofitService.deleteNote(noteId)
        return if (response.isSuccessful) {
            null
        }
        else {
            if (response.code() == 401) {
                NoteRequestError.TokenExpiredError()
            }
            else {
                NoteRequestError.UnknownError()
            }
        }
    }

    override suspend fun getNoteById(noteId: Int): Result<NoteJson> {
        val response = retrofitService.getNoteById(noteId)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(NoteRequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(NoteRequestError.TokenExpiredError())
            }
            else {
                Result.failure(NoteRequestError.UnknownError())
            }
        }
    }

    override suspend fun getCreateNoteScreenBdUi(): Result<ComponentJson> {
        val response = retrofitService.getCreateNoteScreenBdUi()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.failure(BdUiRequestError.UnknownError())
            }
            else {
                Result.success(body)
            }
        }
        else {
            if (response.code() == 401) {
                Result.failure(BdUiRequestError.TokenExpiredError())
            }
            else {
                Result.failure(BdUiRequestError.UnknownError())
            }
        }
    }
}