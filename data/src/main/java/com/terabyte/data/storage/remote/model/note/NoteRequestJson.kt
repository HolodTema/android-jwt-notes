package com.terabyte.data.storage.remote.model.note

import kotlinx.serialization.Serializable


@Serializable
data class NoteRequestJson(
    val title: String,
    val content: String
)