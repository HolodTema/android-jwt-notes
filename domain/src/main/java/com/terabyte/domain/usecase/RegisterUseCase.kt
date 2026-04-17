package com.terabyte.domain.usecase

import com.terabyte.domain.model.RegisterCredentialsModel
import com.terabyte.domain.model.RegistrationError
import com.terabyte.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    // if error is null, then registration is successful
    suspend operator fun invoke(registerCredentialsModel: RegisterCredentialsModel): RegistrationError? {
        val result = authRepository.register(registerCredentialsModel)
        if (result.isFailure) {
            return result.exceptionOrNull() as RegistrationError?
        }
        return null
    }
}