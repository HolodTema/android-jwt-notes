package com.terabyte.data.storage.remote.model.bdui.attributes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("sizeSpec")
sealed class SizeSpec {

    @Serializable
    @SerialName("fixed")
    data class Fixed(val dp: Int) : SizeSpec()

    @Serializable
    @SerialName("wrapContent")
    object WrapContent : SizeSpec()

    @Serializable
    @SerialName("matchParent")
    object MatchParent : SizeSpec()

    @Serializable
    @SerialName("weight")
    data class Weight(
        val weight: Float
    ) : SizeSpec()

}


@Serializable
@SerialName("padding")
data class Padding(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


@Serializable
@SerialName("margin")
data class Margin(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


@Serializable
@SerialName("verticalAlignment")
sealed class VerticalAlignment {

    @Serializable
    @SerialName("center")
    object Center : VerticalAlignment()

    @Serializable
    @SerialName("top")
    object Top : VerticalAlignment()

    @Serializable
    @SerialName("bottom")
    object Bottom : VerticalAlignment()

}


@Serializable
@SerialName("horizontalAlignment")
sealed class HorizontalAlignment {

    @Serializable
    @SerialName("center")
    object Center : HorizontalAlignment()

    @Serializable
    @SerialName("end")
    object End : HorizontalAlignment()

    @Serializable
    @SerialName("start")
    object Start : HorizontalAlignment()

}

@Serializable
@SerialName("verticalArrangement")
sealed class VerticalArrangement {


    @Serializable
    @SerialName("center")
    object Center : VerticalArrangement()


    @Serializable
    @SerialName("top")
    object Top : VerticalArrangement()


    @Serializable
    @SerialName("bottom")
    object Bottom : VerticalArrangement()


    @Serializable
    @SerialName("spaceBetween")
    object SpaceBetween : VerticalArrangement()


    @Serializable
    @SerialName("spaceEvenly")
    object SpaceEvenly : VerticalArrangement()


    @Serializable
    @SerialName("spaceAround")
    object SpaceAround : VerticalArrangement()


}


@Serializable
@SerialName("horizontalArrangement")
sealed class HorizontalArrangement {

    @Serializable
    @SerialName("center")
    object Center : HorizontalArrangement()

    @Serializable
    @SerialName("start")
    object Start : HorizontalArrangement()

    @Serializable
    @SerialName("end")
    object End : HorizontalArrangement()

    @Serializable
    @SerialName("spaceBetween")
    object SpaceBetween : HorizontalArrangement()

    @Serializable
    @SerialName("spaceEvenly")
    object SpaceEvenly : HorizontalArrangement()

    @Serializable
    @SerialName("spaceAround")
    object SpaceAround : HorizontalArrangement()

}
