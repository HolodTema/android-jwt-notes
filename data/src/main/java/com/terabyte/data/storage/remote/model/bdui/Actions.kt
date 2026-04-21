package com.terabyte.data.storage.remote.model.bdui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("Action")
sealed class Action {
}


@Serializable
@SerialName("ToastAction")
data class ToastAction(
    val message: String
) : Action() {
}


@Serializable
@SerialName("CreateNoteAction")
object CreateNoteAction: Action() {
}
