package com.terabyte.data.storage.remote.model.bdui

import com.terabyte.data.storage.remote.model.bdui.attributes.HorizontalAlignmentJson
import com.terabyte.data.storage.remote.model.bdui.attributes.HorizontalArrangementJson
import com.terabyte.data.storage.remote.model.bdui.attributes.MarginJson
import com.terabyte.data.storage.remote.model.bdui.attributes.PaddingJson
import com.terabyte.data.storage.remote.model.bdui.attributes.SizeSpecJson
import com.terabyte.data.storage.remote.model.bdui.attributes.TextStyleJson
import com.terabyte.data.storage.remote.model.bdui.attributes.VerticalAlignmentJson
import com.terabyte.data.storage.remote.model.bdui.attributes.VerticalArrangementJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class ComponentJson {
    abstract val width: SizeSpecJson
    abstract val height: SizeSpecJson
    abstract val padding: PaddingJson
    abstract val margin: MarginJson
}


@Serializable
@SerialName("Column")
data class ColumnComponentJson(
    override val width: SizeSpecJson,
    override val height: SizeSpecJson,
    override val padding: PaddingJson = PaddingJson(),
    override val margin: MarginJson = MarginJson(),
    val verticalArrangement: VerticalArrangementJson,
    val horizontalAlignment: HorizontalAlignmentJson,
    val backgroundColorHex: String? = null,
    val children: List<ComponentJson>
) : ComponentJson()


@Serializable
@SerialName("Row")
data class RowComponentJson(
    override val width: SizeSpecJson,
    override val height: SizeSpecJson,
    override val padding: PaddingJson = PaddingJson(),
    override val margin: MarginJson = MarginJson(),
    val verticalAlignment: VerticalAlignmentJson,
    val horizontalArrangement: HorizontalArrangementJson,
    val backgroundColorHex: String? = null,
    val children: List<ComponentJson>
) : ComponentJson()


@Serializable
@SerialName("Text")
data class TextComponentJson(
    override val width: SizeSpecJson,
    override val height: SizeSpecJson,
    override val padding: PaddingJson = PaddingJson(),
    override val margin: MarginJson = MarginJson(),
    val text: String,
    val style: TextStyleJson? = null
) : ComponentJson()


@Serializable
@SerialName("TextField")
data class TextFieldComponentJson(
    override val width: SizeSpecJson,
    override val height: SizeSpecJson,
    override val padding: PaddingJson = PaddingJson(),
    override val margin: MarginJson = MarginJson(),
    val id: String,
    val singleLine: Boolean = true,
    val hint: String = "",
    val style: TextStyleJson? = null
) : ComponentJson()


@Serializable
@SerialName("Button")
data class ButtonComponentJson(
    override val width: SizeSpecJson,
    override val height: SizeSpecJson,
    override val padding: PaddingJson = PaddingJson(),
    override val margin: MarginJson = MarginJson(),
    val text: String,
    val textColorHex: String? = null,
    val backgroundColorHex: String? = null,
    val action: ActionJson
) : ComponentJson()
