package com.terabyte.data.storage.remote

import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson

interface NetworkStorage {

    suspend fun login(loginRequestJson: LoginRequestJson): Result<LoginResponseJson>

    suspend fun register()
}