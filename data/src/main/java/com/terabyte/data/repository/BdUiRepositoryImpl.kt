package com.terabyte.data.repository

import com.terabyte.data.storage.remote.NetworkStorage
import com.terabyte.data.storage.remote.model.bdui.ActionJson
import com.terabyte.data.storage.remote.model.bdui.ButtonComponentJson
import com.terabyte.data.storage.remote.model.bdui.ColumnComponentJson
import com.terabyte.data.storage.remote.model.bdui.ComponentJson
import com.terabyte.data.storage.remote.model.bdui.CreateNoteActionJson
import com.terabyte.data.storage.remote.model.bdui.RowComponentJson
import com.terabyte.data.storage.remote.model.bdui.TextComponentJson
import com.terabyte.data.storage.remote.model.bdui.TextFieldComponentJson
import com.terabyte.data.storage.remote.model.bdui.ToastActionJson
import com.terabyte.data.storage.remote.model.bdui.attributes.HorizontalAlignmentJson
import com.terabyte.data.storage.remote.model.bdui.attributes.HorizontalArrangementJson
import com.terabyte.data.storage.remote.model.bdui.attributes.MarginJson
import com.terabyte.data.storage.remote.model.bdui.attributes.PaddingJson
import com.terabyte.data.storage.remote.model.bdui.attributes.SizeSpecJson
import com.terabyte.data.storage.remote.model.bdui.attributes.TextStyleJson
import com.terabyte.data.storage.remote.model.bdui.attributes.VerticalAlignmentJson
import com.terabyte.data.storage.remote.model.bdui.attributes.VerticalArrangementJson
import com.terabyte.domain.model.bdui.Action
import com.terabyte.domain.model.bdui.ButtonComponent
import com.terabyte.domain.model.bdui.ColumnComponent
import com.terabyte.domain.model.bdui.Component
import com.terabyte.domain.model.bdui.RowComponent
import com.terabyte.domain.model.bdui.TextComponent
import com.terabyte.domain.model.bdui.TextFieldComponent
import com.terabyte.domain.model.bdui.attributes.HorizontalAlignment
import com.terabyte.domain.model.bdui.attributes.HorizontalArrangement
import com.terabyte.domain.model.bdui.attributes.Margin
import com.terabyte.domain.model.bdui.attributes.Padding
import com.terabyte.domain.model.bdui.attributes.SizeSpec
import com.terabyte.domain.model.bdui.attributes.TextStyle
import com.terabyte.domain.model.bdui.attributes.VerticalAlignment
import com.terabyte.domain.model.bdui.attributes.VerticalArrangement
import com.terabyte.domain.repository.BdUiRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BdUiRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : BdUiRepository {

    override suspend fun getCreateNoteScreenBdUi(): Result<Component> {
        val result = networkStorage.getCreateNoteScreenBdUi()
        return result.map { componentJson ->
            componentJson.toDomain()
        }
    }

    private fun SizeSpecJson.toDomain(): SizeSpec = when (this) {
        is SizeSpecJson.Fixed -> SizeSpec.Fixed(dp)
        SizeSpecJson.WrapContent -> SizeSpec.WrapContent
        SizeSpecJson.MatchParent -> SizeSpec.MatchParent
        is SizeSpecJson.Weight -> SizeSpec.Weight(weight)
    }

    private fun PaddingJson.toDomain(): Padding = Padding(start, top, end, bottom)
    private fun MarginJson.toDomain(): Margin = Margin(start, top, end, bottom)

    private fun VerticalAlignmentJson.toDomain(): VerticalAlignment = when (this) {
        VerticalAlignmentJson.Center -> VerticalAlignment.Center
        VerticalAlignmentJson.Top -> VerticalAlignment.Top
        VerticalAlignmentJson.Bottom -> VerticalAlignment.Bottom
    }

    private fun HorizontalAlignmentJson.toDomain(): HorizontalAlignment = when (this) {
        HorizontalAlignmentJson.Center -> HorizontalAlignment.Center
        HorizontalAlignmentJson.End -> HorizontalAlignment.End
        HorizontalAlignmentJson.Start -> HorizontalAlignment.Start
    }

    private fun VerticalArrangementJson.toDomain(): VerticalArrangement = when (this) {
        VerticalArrangementJson.Center -> VerticalArrangement.Center
        VerticalArrangementJson.Top -> VerticalArrangement.Top
        VerticalArrangementJson.Bottom -> VerticalArrangement.Bottom
        VerticalArrangementJson.SpaceBetween -> VerticalArrangement.SpaceBetween
        VerticalArrangementJson.SpaceEvenly -> VerticalArrangement.SpaceEvenly
        VerticalArrangementJson.SpaceAround -> VerticalArrangement.SpaceAround
    }

    private fun HorizontalArrangementJson.toDomain(): HorizontalArrangement = when (this) {
        HorizontalArrangementJson.Center -> HorizontalArrangement.Center
        HorizontalArrangementJson.Start -> HorizontalArrangement.Start
        HorizontalArrangementJson.End -> HorizontalArrangement.End
        HorizontalArrangementJson.SpaceBetween -> HorizontalArrangement.SpaceBetween
        HorizontalArrangementJson.SpaceEvenly -> HorizontalArrangement.SpaceEvenly
        HorizontalArrangementJson.SpaceAround -> HorizontalArrangement.SpaceAround
    }

    private fun TextStyleJson.toDomain(): TextStyle = TextStyle(fontSize, colorHex)

    private fun ActionJson.toDomain(): Action = when (this) {
        CreateNoteActionJson -> Action.CreateNoteAction
        is ToastActionJson -> Action.ToastAction(message)
    }

    private fun ComponentJson.toDomain(): Component = when (this) {
        is ColumnComponentJson -> ColumnComponent(
            width = width.toDomain(),
            height = height.toDomain(),
            padding = padding.toDomain(),
            margin = margin.toDomain(),
            verticalArrangement = verticalArrangement.toDomain(),
            horizontalAlignment = horizontalAlignment.toDomain(),
            backgroundColorHex = backgroundColorHex,
            children = children.map { it.toDomain() }
        )
        is RowComponentJson -> RowComponent(
            width = width.toDomain(),
            height = height.toDomain(),
            padding = padding.toDomain(),
            margin = margin.toDomain(),
            verticalAlignment = verticalAlignment.toDomain(),
            horizontalArrangement = horizontalArrangement.toDomain(),
            backgroundColorHex = backgroundColorHex,
            children = children.map { it.toDomain() }
        )
        is TextComponentJson -> TextComponent(
            width = width.toDomain(),
            height = height.toDomain(),
            padding = padding.toDomain(),
            margin = margin.toDomain(),
            text = text,
            style = style?.toDomain()
        )
        is TextFieldComponentJson -> TextFieldComponent(
            width = width.toDomain(),
            height = height.toDomain(),
            padding = padding.toDomain(),
            margin = margin.toDomain(),
            singleLine = singleLine,
            hint = hint,
            style = style?.toDomain()
        )
        is ButtonComponentJson -> ButtonComponent(
            width = width.toDomain(),
            height = height.toDomain(),
            padding = padding.toDomain(),
            margin = margin.toDomain(),
            text = text,
            textColorHex = textColorHex,
            backgroundColorHex = backgroundColorHex,
            action = action.toDomain()
        )
    }
}