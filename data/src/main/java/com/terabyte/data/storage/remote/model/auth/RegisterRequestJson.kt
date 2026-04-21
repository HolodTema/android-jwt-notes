package com.terabyte.data.storage.remote.model.auth

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequestJson(
    val username: String,
    val email: String,
    val password: String
)
