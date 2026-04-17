package com.terabyte.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ValidateUsernameUseCase @Inject constructor() {

    operator fun invoke(username: String): Boolean {
        if (username.isBlank()) {
            return false
        }

        val alphabet = "abcdefghijkmnlopqrstuvwxyz_1234567890"
        for (ch in username.lowercase()) {
            if (ch !in alphabet) {
                return false
            }
        }
        return true
    }
}