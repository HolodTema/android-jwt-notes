package com.terabyte.domain.repository

import com.terabyte.domain.model.bdui.Component

interface BdUiRepository {

    suspend fun getCreateNoteScreenBdUi(): Result<Component>

}