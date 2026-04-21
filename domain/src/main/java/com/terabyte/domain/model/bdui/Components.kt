package com.terabyte.domain.model.bdui

import com.terabyte.domain.model.bdui.attributes.HorizontalAlignment
import com.terabyte.domain.model.bdui.attributes.HorizontalArrangement
import com.terabyte.domain.model.bdui.attributes.Margin
import com.terabyte.domain.model.bdui.attributes.Padding
import com.terabyte.domain.model.bdui.attributes.SizeSpec
import com.terabyte.domain.model.bdui.attributes.TextStyle
import com.terabyte.domain.model.bdui.attributes.VerticalAlignment
import com.terabyte.domain.model.bdui.attributes.VerticalArrangement


sealed class Component {
    abstract val width: SizeSpec
    abstract val height: SizeSpec
    abstract val padding: Padding
    abstract val margin: Margin
}


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


data class TextComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val text: String,
    val style: TextStyle? = null
) : Component()


data class TextFieldComponent(
    override val width: SizeSpec,
    override val height: SizeSpec,
    override val padding: Padding = Padding(),
    override val margin: Margin = Margin(),
    val id: String,
    val singleLine: Boolean = true,
    val hint: String = "",
    val style: TextStyle? = null
) : Component()


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

