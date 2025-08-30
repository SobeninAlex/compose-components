package ru.sobeninalex.compose.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Форма карточки в столбце
 * Первая карточка имеет скуругленный верх
 * Нижняя карточка - скругленный низ
 * В серединке приямоугольные карточки
 */
fun columnCardShape(
    index: Int,
    count: Int,
    radius: Dp = 20.dp
): Shape {
    return if (count <= 1) {
        RoundedCornerShape(radius)
    } else if (index == 0) {
        RoundedCornerShape(topStart = radius, topEnd = radius)
    } else if (index == count - 1) {
        RoundedCornerShape(bottomStart = radius, bottomEnd = radius)
    } else {
        RectangleShape
    }
}
