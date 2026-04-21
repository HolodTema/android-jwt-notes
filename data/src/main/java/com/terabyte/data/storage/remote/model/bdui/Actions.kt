package com.terabyte.data.storage.remote.model.bdui

import com.google.gson.annotations.SerializedName

@SerializedName("Action")
sealed class ActionJson {

    object CreateNoteActionJson : ActionJson()

    data class ToastAction(
        val message: String
    ) : ActionJson()
}


