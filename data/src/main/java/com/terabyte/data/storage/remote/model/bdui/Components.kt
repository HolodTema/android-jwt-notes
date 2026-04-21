package com.terabyte.data.storage.remote.model.bdui

import com.terabyte.data.storage.remote.model.bdui.attributes.HorizontalAlignment
import com.terabyte.data.storage.remote.model.bdui.attributes.HorizontalArrangement
import com.terabyte.data.storage.remote.model.bdui.attributes.Margin
import com.terabyte.data.storage.remote.model.bdui.attributes.Padding
import com.terabyte.data.storage.remote.model.bdui.attributes.SizeSpec
import com.terabyte.data.storage.remote.model.bdui.attributes.TextStyle
import com.terabyte.data.storage.remote.model.bdui.attributes.VerticalAlignment
import com.terabyte.data.storage.remote.model.bdui.attributes.VerticalArrangement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("Component")
sealed class Component {
    abstract val width: SizeSpec
    abstract val height: SizeSpec
    abstract val padding: Padding
    abstract val margin: Margin
}


@Serializable
@SerialName("Column")
data class ColumnComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val verticalArrangement: VerticalArrangement,
    val horizontalAlignment: HorizontalAlignment,
    val backgroundColorHex: String? = null,
    val children: List<Component>
) : Component()


@Serializable
@SerialName("Row")
data class RowComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val verticalAlignment: VerticalAlignment,
    val horizontalArrangement: HorizontalArrangement,
    val backgroundColorHex: String? = null,
    val children: List<Component>
) : Component()


@Serializable
@SerialName("Text")
data class TextComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val text: String,
    val style: TextStyle? = null
) : Component()


@Serializable
@SerialName("TextField")
data class TextFieldComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val singleLine: Boolean = true,
    val hint: String = "",
    val style: TextStyle? = null
) : Component()


@Serializable
@SerialName("Button")
data class ButtonComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val text: String,
    val textColorHex: String? = null,
    val backgroundColorHex: String? = null,
    val action: Action
) : Component()
