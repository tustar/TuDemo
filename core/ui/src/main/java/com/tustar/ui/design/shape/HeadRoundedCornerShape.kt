package com.tustar.ui.design.shape

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CupHeadRoundedCornerShape(
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    cupHeadRadius: CornerSize,
) : HeadCornerBasedShape(
    topStart = topStart,
    topEnd = topEnd,
    bottomEnd = bottomEnd,
    bottomStart = bottomStart,
    cupHeadRadius = cupHeadRadius,
) {

    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        cupHeadRadius: Float,
        layoutDirection: LayoutDirection,
    ): Outline {
        val roundRect = RoundRect(
            rect = Rect(
                Offset(0.0f, cupHeadRadius * 0.75f),
                Size(size.width, size.height - cupHeadRadius)
            ),
            topLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd),
            topRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart),
            bottomRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart),
            bottomLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd)
        )
        val path = Path().apply {
            // Cup Head
            addArc(
                Rect(
                    size.width / 2 - cupHeadRadius,
                    0.0f,
                    size.width / 2 + cupHeadRadius,
                    cupHeadRadius * 2
                ),
                0.0f,
                -180.0f
            )
            // Round Rect
            addRoundRect(roundRect)
        }
        return Outline.Generic(path)
    }

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize,
        cupHeadRadius: CornerSize,
    ) = CupHeadRoundedCornerShape(
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart,
        cupHeadRadius = cupHeadRadius,
    )

    override fun toString(): String {
        return "CupHeadRoundedCornerShape(topStart = $topStart, topEnd = $topEnd, bottomEnd = " +
                "$bottomEnd, bottomStart = $bottomStart, cupHeadRadius = $cupHeadRadius)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CupHeadRoundedCornerShape) return false

        if (topStart != other.topStart) return false
        if (topEnd != other.topEnd) return false
        if (bottomEnd != other.bottomEnd) return false
        if (bottomStart != other.bottomStart) return false
        if (cupHeadRadius != other.cupHeadRadius) return false

        return true
    }

    override fun hashCode(): Int {
        var result = topStart.hashCode()
        result = 31 * result + topEnd.hashCode()
        result = 31 * result + bottomEnd.hashCode()
        result = 31 * result + bottomStart.hashCode()
        result = 31 * result + cupHeadRadius.hashCode()
        return result
    }
}


/**
 * Creates [CupHeadRoundedCornerShape] with the same size applied for all four corners.
 * @param corner [CornerSize] to apply.
 */
fun CupHeadRoundedCornerShape(corner: CornerSize, cupHeadRadius: CornerSize) =
    CupHeadRoundedCornerShape(corner, corner, corner, corner, cupHeadRadius)

/**
 * Creates [CupHeadRoundedCornerShape] with the same size applied for all four corners.
 * @param size Size in [Dp] to apply.
 */
fun CupHeadRoundedCornerShape(size: Dp, cupHeadRadius: Dp) = CupHeadRoundedCornerShape(
    CornerSize(size),
    CornerSize(cupHeadRadius)
)


/**
 * Creates [CupHeadRoundedCornerShape] with sizes defined in [Dp].
 */
fun CupHeadRoundedCornerShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    cupHeadRadius: Dp = 0.dp
) = CupHeadRoundedCornerShape(
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart),
    cupHeadRadius = CornerSize(cupHeadRadius)
)
