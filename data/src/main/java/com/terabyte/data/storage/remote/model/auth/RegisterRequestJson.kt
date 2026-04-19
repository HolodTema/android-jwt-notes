package com.terabyte.data.storage.remote.model.auth

data class RegisterRequestJson(
    val username: String,
    val email: String,
    val password: String
)
