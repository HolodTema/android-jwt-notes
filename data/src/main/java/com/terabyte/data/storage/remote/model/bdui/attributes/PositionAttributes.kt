package com.terabyte.data.storage.remote.model.bdui.attributes



sealed class SizeSpec {

    object WrapContent : SizeSpec()

    object MatchParent : SizeSpec()

    data class Weight(
        val weight: Float
    ) : SizeSpec()

    data class Fixed(val dp: Int) : SizeSpec()
}

data class Padding(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


data class Margin(
    val start: Int = 0,
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0
)


sealed class VerticalAlignment {

    object Center : VerticalAlignment()

    object Top : VerticalAlignment()

    object Bottom : VerticalAlignment()
}


sealed class HorizontalAlignment {

    object Center : HorizontalAlignment()

    object End : HorizontalAlignment()

    object Start : HorizontalAlignment()
}


sealed class VerticalArrangement {

    object Center : VerticalArrangement()

    object Top : VerticalArrangement()

    object Bottom : VerticalArrangement()

    object SpaceBetween : VerticalArrangement()

    object SpaceEvenly : VerticalArrangement()

    object SpaceAround : VerticalArrangement()


}


sealed class HorizontalArrangement {

    object Center : HorizontalArrangement()

    object Start : HorizontalArrangement()

    object End : HorizontalArrangement()

    object SpaceBetween : HorizontalArrangement()

    object SpaceEvenly : HorizontalArrangement()

    object SpaceAround : HorizontalArrangement()
}

