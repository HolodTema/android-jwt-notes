package com.terabyte.data.storage.remote.model.bdui.attributes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class SizeSpecJson {

    @Serializable
    @SerialName("fixed")
    data class Fixed(val dp: Int) : SizeSpecJson()


    @Serializable
    @SerialName("wrapContent")
    object WrapContent : SizeSpecJson()

    @Serializable
    @SerialName("matchParent")
    object MatchParent : SizeSpecJson()

    @Serializable
    @SerialName("weight")
    data class Weight(
        val weight: Float
    ) : SizeSpecJson()

}


@Serializable
@SerialName("padding")
data class PaddingJson(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


@Serializable
@SerialName("margin")
data class MarginJson(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


@Serializable
sealed class VerticalAlignmentJson {

    @Serializable
    @SerialName("center")
    object Center : VerticalAlignmentJson()

    @Serializable
    @SerialName("top")
    object Top : VerticalAlignmentJson()

    @Serializable
    @SerialName("bottom")
    object Bottom : VerticalAlignmentJson()

}


@Serializable
sealed class HorizontalAlignmentJson {

    @Serializable
    @SerialName("center")
    object Center : HorizontalAlignmentJson()

    @Serializable
    @SerialName("end")
    object End : HorizontalAlignmentJson()

    @Serializable
    @SerialName("start")
    object Start : HorizontalAlignmentJson()

}

@Serializable
sealed class VerticalArrangementJson {


    @Serializable
    @SerialName("center")
    object Center : VerticalArrangementJson()


    @Serializable
    @SerialName("top")
    object Top : VerticalArrangementJson()


    @Serializable
    @SerialName("bottom")
    object Bottom : VerticalArrangementJson()


    @Serializable
    @SerialName("spaceBetween")
    object SpaceBetween : VerticalArrangementJson()


    @Serializable
    @SerialName("spaceEvenly")
    object SpaceEvenly : VerticalArrangementJson()


    @Serializable
    @SerialName("spaceAround")
    object SpaceAround : VerticalArrangementJson()


}


@Serializable
sealed class HorizontalArrangementJson {

    @Serializable
    @SerialName("center")
    object Center : HorizontalArrangementJson()

    @Serializable
    @SerialName("start")
    object Start : HorizontalArrangementJson()

    @Serializable
    @SerialName("end")
    object End : HorizontalArrangementJson()

    @Serializable
    @SerialName("spaceBetween")
    object SpaceBetween : HorizontalArrangementJson()

    @Serializable
    @SerialName("spaceEvenly")
    object SpaceEvenly : HorizontalArrangementJson()

    @Serializable
    @SerialName("spaceAround")
    object SpaceAround : HorizontalArrangementJson()

}
