package com.terabyte.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return email.matches(emailRegex)
    }
}