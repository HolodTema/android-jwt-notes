package com.terabyte.data.storage.remote.model.auth

data class LoginResponseJson(
    val token: String,
    val username: String,
)
