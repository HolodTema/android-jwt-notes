package com.terabyte.domain.model.auth

data class RegisterCredentialsModel(
    val username: String,
    val email: String,
    val password: String
)