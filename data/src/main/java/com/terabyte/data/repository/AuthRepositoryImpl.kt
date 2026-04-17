package com.terabyte.data.repository

import com.terabyte.data.storage.remote.NetworkStorage
import com.terabyte.data.storage.remote.model.LoginRequestJson
import com.terabyte.data.storage.remote.model.LoginResponseJson
import com.terabyte.domain.model.LoginCredentialsModel
import com.terabyte.domain.model.RegisterCredentialsModel
import com.terabyte.domain.model.TokenModel
import com.terabyte.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : AuthRepository {

    override suspend fun login(loginCredentialsModel: LoginCredentialsModel): Result<TokenModel> {
        val loginRequestJson = mapToLoginRequestJson(loginCredentialsModel)

        val result = networkStorage.login(loginRequestJson)
        return result.map {
            mapToTokenModel(it)
        }
    }

    override suspend fun register(registerCredentialsModel: RegisterCredentialsModel): Result<TokenModel> {
        return Result.failure(Exception())
    }

    override suspend fun logout() {

    }

    private fun mapToLoginRequestJson(loginCredentialsModel: LoginCredentialsModel): LoginRequestJson {
        return LoginRequestJson(
            username = loginCredentialsModel.username,
            password = loginCredentialsModel.password
        )
    }

    private fun mapToTokenModel(loginResponseJson: LoginResponseJson): TokenModel {
        return TokenModel(
            token = loginResponseJson.token
        )
    }
}