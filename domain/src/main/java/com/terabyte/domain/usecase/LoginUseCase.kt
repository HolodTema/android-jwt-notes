package com.terabyte.domain.usecase

import com.terabyte.domain.model.LoginCredentialsModel
import com.terabyte.domain.repository.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(loginCredentialsModel: LoginCredentialsModel) {
        authRepository.login(loginCredentialsModel)
    }
}