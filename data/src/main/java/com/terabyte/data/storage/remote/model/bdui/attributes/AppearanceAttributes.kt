package com.terabyte.data.storage.remote.model.bdui.attributes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("textStyle")
data class TextStyle(
    val fontSize: Int? = null,
    val colorHex: String? = null
)
