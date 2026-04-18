package com.terabyte.data.repository

import com.terabyte.data.storage.remote.NetworkStorage
import com.terabyte.data.storage.remote.model.NoteJson
import com.terabyte.domain.model.NoteModel
import com.terabyte.domain.repository.NoteRepository
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : NoteRepository {

    override suspend fun getAllNotes(): Result<List<NoteModel>> {
        return networkStorage.getAllNotes().map { listNotes ->
            listNotes.map { noteJson ->

            }
        }
    }

    private fun mapToNoteModel(noteJson: NoteJson): NoteModel {
        val dateFormat = SimpleDateFormat("yyyy-MM-DDThh:mm:ss", Locale.getDefault())

        
        return NoteModel(
            id = noteJson.id,
            userId = noteJson.userId,
            title = noteJson.title,
            content = noteJson.content,
            createdAt = dateFormat.format(noteJson.createdAt),
            updatedAt = noteJson.updatedAt
        )
    }
}