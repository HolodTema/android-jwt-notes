package com.terabyte.domain.usecase.auth

import com.terabyte.domain.repository.TokenRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke() {
        tokenRepository.deleteToken()
    }
}