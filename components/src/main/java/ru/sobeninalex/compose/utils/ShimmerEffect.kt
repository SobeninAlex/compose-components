package ru.sobeninalex.compose.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import ru.sobeninalex.compose.theme.BlackColor05

/** на белой color = BlackColor05
 * на любом другом color = WhiteColor50
 */
fun Modifier.shimmerEffect(
    isEnabled: Boolean = true,
    color: Color = BlackColor05,
    durationsMills: Int = 2000,
): Modifier = composed {
    if (!isEnabled) return@composed Modifier

    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        label = "",
        initialValue = -1f * size.width.toFloat(),
        targetValue = 1f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationsMills,
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    drawBehind {
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    color,
                    color,
                    Color.Transparent
                ),
                startX = translateAnimation,
                endX = translateAnimation + size.width.toFloat()
            )
        )
    }.onGloballyPositioned {
        size = it.size
    }
}