package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.data.storage.remote.model.RegisterRequestJson
import com.terabyte.data.storage.remote.model.UserDetailsJson

interface NetworkStorage {

    suspend fun login(loginRequestJson: LoginRequestJson): Result<LoginResponseJson>

    suspend fun register(registerRequestJson: RegisterRequestJson): Result<LoginResponseJson>

    suspend fun getAllNotes(): Result<List<NoteJson>>

    suspend fun getUserDetails(): Result<UserDetailsJson>

}