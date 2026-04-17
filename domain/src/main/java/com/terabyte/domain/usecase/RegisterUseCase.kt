package com.terabyte.domain.usecase

import com.terabyte.domain.model.RegisterCredentialsModel
import com.terabyte.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(registerCredentialsModel: RegisterCredentialsModel) {
        authRepository.register(registerCredentialsModel)
    }
}