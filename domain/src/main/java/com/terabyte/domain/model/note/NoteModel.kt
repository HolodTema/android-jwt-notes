package com.terabyte.domain.model.note

import java.time.LocalDateTime

data class NoteModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)