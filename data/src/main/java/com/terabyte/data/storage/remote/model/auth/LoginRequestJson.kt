package com.terabyte.data.storage.remote.model.auth

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestJson(
    val username: String,
    val password: String
)
