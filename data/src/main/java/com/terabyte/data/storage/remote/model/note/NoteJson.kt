package com.terabyte.data.storage.remote.model.note

data class NoteJson(
    val id: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)