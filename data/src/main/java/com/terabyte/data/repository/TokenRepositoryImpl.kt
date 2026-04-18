package com.terabyte.data.repository

import com.terabyte.data.storage.local.datastore.TokenDataStore
import com.terabyte.domain.model.TokenModel
import com.terabyte.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenRepository {

    override suspend fun saveToken(tokenModel: TokenModel) {
        tokenDataStore.saveToken(tokenModel.token)
    }

    override suspend fun getToken(): TokenModel? {
        val token = tokenDataStore.getToken()
        token?.let {
            return TokenModel(token)
        }
        return null
    }

    override suspend fun deleteToken() {
        tokenDataStore.deleteToken()
    }
}