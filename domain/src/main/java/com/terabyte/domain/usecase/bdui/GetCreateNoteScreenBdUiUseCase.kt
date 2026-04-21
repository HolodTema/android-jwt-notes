package com.terabyte.domain.usecase.bdui

import com.terabyte.domain.model.bdui.Component
import com.terabyte.domain.repository.BdUiRepository
import javax.inject.Inject

class GetCreateNoteScreenBdUiUseCase @Inject constructor(
    private val bdUiRepository: BdUiRepository
) {

    suspend operator fun invoke(): Result<Component> {
        return bdUiRepository.getCreateNoteScreenBdUi()
    }
}