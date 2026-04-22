package com.terabyte.data.storage.remote.model.bdui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class ActionJson


@Serializable
@SerialName("ToastAction")
data class ToastActionJson (
    val message: String
) : ActionJson() {
}


@Serializable
@SerialName("CreateNoteAction")
object CreateNoteActionJson : ActionJson() {
}
