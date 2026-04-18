package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.data.storage.remote.model.RegisterRequestJson

interface NetworkStorage {

    suspend fun login(loginRequestJson: LoginRequestJson): Result<LoginResponseJson>

    suspend fun register(registerRequestJson: RegisterRequestJson): Result<LoginResponseJson>

    suspend fun getAllNotes(): Result<List<NoteJson>>

}