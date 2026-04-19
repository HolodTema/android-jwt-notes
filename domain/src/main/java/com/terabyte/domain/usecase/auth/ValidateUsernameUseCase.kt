package com.terabyte.domain.usecase.auth

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.iterator


@Singleton
class ValidateUsernameUseCase @Inject constructor() {

    operator fun invoke(username: String): Boolean {
        if (username.isBlank()) {
            return false
        }

        val alphabet = "abcdefghijkmnlopqrstuvwxyz1234567890"
        for (ch in username.lowercase()) {
            if (ch !in alphabet) {
                return false
            }
        }
        return true
    }
}