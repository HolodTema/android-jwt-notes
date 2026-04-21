package com.terabyte.data.storage.remote.model.user

import kotlinx.serialization.Serializable


@Serializable
data class UserDetailsJson(
    val username: String,
    val email: String
)