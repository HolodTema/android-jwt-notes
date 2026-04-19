package com.terabyte.domain.usecase.auth

import com.terabyte.domain.model.auth.RegisterCredentialsModel
import com.terabyte.domain.model.error.RegistrationError
import com.terabyte.domain.repository.AuthRepository
import com.terabyte.domain.repository.TokenRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    // if error is null, then registration is successful
    suspend operator fun invoke(registerCredentialsModel: RegisterCredentialsModel): RegistrationError? {
        val result = authRepository.register(registerCredentialsModel)
        if (result.isFailure) {
            return result.exceptionOrNull() as RegistrationError?
        }

        tokenRepository.saveToken(result.getOrThrow())
        return null
    }
}