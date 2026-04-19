package com.terabyte.domain.repository

import com.terabyte.domain.model.auth.LoginCredentialsModel
import com.terabyte.domain.model.auth.RegisterCredentialsModel
import com.terabyte.domain.model.auth.TokenModel

interface AuthRepository {

    suspend fun login(loginCredentialsModel: LoginCredentialsModel): Result<TokenModel>

    suspend fun register(registerCredentialsModel: RegisterCredentialsModel): Result<TokenModel>

}