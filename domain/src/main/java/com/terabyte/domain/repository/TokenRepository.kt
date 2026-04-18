package com.terabyte.domain.repository

import com.terabyte.domain.model.TokenModel

interface TokenRepository {

    suspend fun saveToken(tokenModel: TokenModel)

    suspend fun getToken(): TokenModel?

    suspend fun deleteToken()

}