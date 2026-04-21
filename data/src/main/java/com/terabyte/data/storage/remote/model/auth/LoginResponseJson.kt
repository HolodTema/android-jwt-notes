package com.terabyte.data.storage.remote.model.auth

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponseJson(
    val token: String,
    val username: String,
)
