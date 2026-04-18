package com.terabyte.domain.usecase

import com.terabyte.domain.model.LoginCredentialsModel
import com.terabyte.domain.repository.AuthRepository
import com.terabyte.domain.repository.TokenRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(loginCredentialsModel: LoginCredentialsModel): Boolean {
        val result = authRepository.login(loginCredentialsModel)
        if (result.isFailure) {
            return false
        }

        tokenRepository.saveToken(result.getOrThrow())
        return true
    }
}