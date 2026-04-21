package com.terabyte.domain.model.bdui

sealed class Action {

    object CreateNoteAction : Action()

    data class ToastAction(
        val message: String
    ) : Action()
}


