package com.terabyte.domain.repository

import com.terabyte.domain.model.LoginCredentialsModel
import com.terabyte.domain.model.RegisterCredentialsModel
import com.terabyte.domain.model.TokenModel

interface AuthRepository {

    suspend fun login(loginCredentialsModel: LoginCredentialsModel): Result<TokenModel>

    suspend fun register(registerCredentialsModel: RegisterCredentialsModel): Result<TokenModel>

    suspend fun logout()
}