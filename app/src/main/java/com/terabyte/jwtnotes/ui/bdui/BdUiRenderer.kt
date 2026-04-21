package com.terabyte.jwtnotes.ui.bdui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.terabyte.domain.model.bdui.Component
import com.terabyte.domain.model.bdui.TextComponent
import com.terabyte.domain.model.bdui.attributes.HorizontalAlignment
import com.terabyte.domain.model.bdui.attributes.HorizontalArrangement
import com.terabyte.domain.model.bdui.attributes.Margin
import com.terabyte.domain.model.bdui.attributes.Padding
import com.terabyte.domain.model.bdui.attributes.SizeSpec
import com.terabyte.domain.model.bdui.attributes.TextStyle
import com.terabyte.domain.model.bdui.attributes.VerticalAlignment
import com.terabyte.domain.model.bdui.attributes.VerticalArrangement
import androidx.compose.ui.text.TextStyle as ComposeTextStyle
import android.graphics.Color as AndroidColor
import androidx.core.graphics.toColorInt
import com.terabyte.domain.model.bdui.Action
import com.terabyte.domain.model.bdui.ButtonComponent
import com.terabyte.domain.model.bdui.TextFieldComponent
import com.terabyte.jwtnotes.ui.bdui.padding


@Composable
fun  BdUiRenderer(component: Component) {

}


private fun String?.toComposeColor(): Color {
    if (this == null) {
        return Color.Unspecified
    }
    return Color(this.toColorInt())
}


private fun TextStyle?.toComposeTextStyle(): ComposeTextStyle {
    val composeColor = this?.colorHex.toComposeColor()

    return ComposeTextStyle(
        fontSize = (this?.fontSize ?: 14).sp,
        color = composeColor
    )
}


private fun Modifier.convertSizeSpecToWidth(sizeSpec: SizeSpec): Modifier {
    return when(sizeSpec) {
        is SizeSpec.Fixed -> {
            this.width(sizeSpec.dp.dp)
        }
        SizeSpec.MatchParent -> {
            this.fillMaxWidth()
        }
        is SizeSpec.Weight -> {
            this
            // do nothing, because of weight will be handled
            // only if component is in RowScope or ColumnScope
        }
        SizeSpec.WrapContent -> {
            this.wrapContentWidth()
        }
    }
}


private fun Modifier.convertSizeSpecToHeight(sizeSpec: SizeSpec): Modifier {
    return when(sizeSpec) {
        is SizeSpec.Fixed -> {
            this.height(sizeSpec.dp.dp)
        }
        SizeSpec.MatchParent -> {
            this.fillMaxHeight()
        }
        is SizeSpec.Weight -> {
            this
            // do nothing, because of weight will be handled
            // only if component is in RowScope or ColumnScope
        }
        SizeSpec.WrapContent -> {
            this.wrapContentHeight()
        }
    }
}


private fun Modifier.margin(margin: Margin): Modifier {
    return this.padding(
        top = margin.top.dp,
        bottom = margin.bottom.dp,
        start = margin.start.dp,
        end = margin.end.dp
    )
}


private fun Modifier.padding(padding: Padding): Modifier {
    return this.padding(
        top = padding.top.dp,
        bottom = padding.bottom.dp,
        start = padding.start.dp,
        end = padding.end.dp
    )
}


private fun HorizontalAlignment.toComposeAlignment(): Alignment.Horizontal {
    return when (this) {
        HorizontalAlignment.Center -> {
            Alignment.CenterHorizontally
        }
        HorizontalAlignment.End -> {
            Alignment.End
        }
        HorizontalAlignment.Start -> {
            Alignment.Start
        }
    }
}


private fun VerticalAlignment.toComposeAlignment(): Alignment.Vertical {
    return when (this) {
        VerticalAlignment.Center -> {
            Alignment.CenterVertically
        }
        VerticalAlignment.Top -> {
            Alignment.Top
        }
        VerticalAlignment.Bottom -> {
            Alignment.Bottom
        }
    }
}


private fun HorizontalArrangement.toComposeArrangement(): Arrangement.Horizontal {
    return when (this) {
        HorizontalArrangement.Center -> {
            Arrangement.Center
        }
        HorizontalArrangement.End -> {
            Arrangement.End
        }
        HorizontalArrangement.SpaceAround -> {
            Arrangement.SpaceAround
        }
        HorizontalArrangement.SpaceBetween -> {
            Arrangement.SpaceBetween
        }
        HorizontalArrangement.SpaceEvenly -> {
            Arrangement.SpaceEvenly
        }
        HorizontalArrangement.Start -> {
            Arrangement.Start
        }
    }
}


private fun VerticalArrangement.toComposeArrangement(): Arrangement.Vertical {
    return when (this) {
        VerticalArrangement.Center -> {
            Arrangement.Center
        }
        VerticalArrangement.SpaceAround -> {
            Arrangement.SpaceAround
        }
        VerticalArrangement.SpaceBetween -> {
            Arrangement.SpaceBetween
        }
        VerticalArrangement.SpaceEvenly -> {
            Arrangement.SpaceEvenly
        }
        VerticalArrangement.Bottom -> {
            Arrangement.Bottom
        }
        VerticalArrangement.Top -> {
            Arrangement.Top
        }
    }
}


@Composable
private fun RenderText(textComponent: TextComponent) {
    Text(
        text = textComponent.text,
        style = textComponent.style.toComposeTextStyle(),
        modifier = Modifier
            .margin(textComponent.margin)
            .convertSizeSpecToWidth(textComponent.width)
            .convertSizeSpecToHeight(textComponent.height)
            .padding(textComponent.padding)

    )
}


@Composable
private fun RenderButton(
    buttonComponent: ButtonComponent,
    onAction: (Action) -> Unit
) {
    val backgroundColor = buttonComponent.backgroundColorHex.toComposeColor()
    val textColor = buttonComponent.textColorHex.toComposeColor()

    Button(
        onClick = {
            onAction(buttonComponent.action)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .margin(buttonComponent.margin)
            .convertSizeSpecToWidth(buttonComponent.width)
            .convertSizeSpecToHeight(buttonComponent.height)
            .padding(buttonComponent.padding)
    ) {
        Text(
            text = buttonComponent.text,
            color = textColor
        )
    }
}


@Composable
fun RenderTextField(
    textFieldComponent: TextFieldComponent,
    mapTextFieldStates: Map<String, String>,
    onValueChange: (String, String) -> Unit,
    stateId: String
) {
    val currentValue = mapTextFieldStates[stateId] ?: ""
    val composeTextStyle = textFieldComponent.style.toComposeTextStyle()

    OutlinedTextField(
        value = currentValue,
        onValueChange = {
            onValueChange(stateId, it)
        },
        label = {
            Text(
                text = textFieldComponent.hint
            )
        },
        singleLine = textFieldComponent.singleLine,
        textStyle = composeTextStyle,
        modifier = Modifier
            .margin(textFieldComponent.margin)
            .convertSizeSpecToWidth(textFieldComponent.width)
            .convertSizeSpecToHeight(textFieldComponent.height)
            .padding(textFieldComponent.padding)
    )
}