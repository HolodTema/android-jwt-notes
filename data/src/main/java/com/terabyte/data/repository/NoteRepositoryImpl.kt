package com.terabyte.data.repository

import com.terabyte.data.storage.remote.NetworkStorage
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.data.storage.remote.model.NoteRequestJson
import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.model.NoteRequestError
import com.terabyte.domain.repository.NoteRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : NoteRepository {

    override suspend fun getAllNotes(): Result<List<NoteModel>> {
        return networkStorage.getAllNotes().map { listNotes ->
            listNotes.map { noteJson ->
                mapToNoteModel(noteJson)
            }
        }
    }

    override suspend fun createNote(title: String, content: String): NoteRequestError? {
        val noteRequestJson = NoteRequestJson(title, content)
        return networkStorage.createNote(noteRequestJson)
    }

    private fun mapToNoteModel(noteJson: NoteJson): NoteModel {
        return NoteModel(
            id = noteJson.id,
            userId = noteJson.userId,
            title = noteJson.title,
            content = noteJson.content,
            createdAt = LocalDateTime.parse(noteJson.createdAt),
            updatedAt = LocalDateTime.parse(noteJson.updatedAt),
        )
    }
}