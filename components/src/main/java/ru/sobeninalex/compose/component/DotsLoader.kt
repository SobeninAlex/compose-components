package ru.sobeninalex.compose.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.sobeninalex.compose.theme.GrayColor

@Composable
fun DotsLoader(
    modifier: Modifier = Modifier,
    dotsColor: Color = GrayColor,
    dotsSize: Dp = 8.dp,
    dotsCount: Int = 3,
    spaceBetween: Dp = 4.dp,
    travelDistance: Dp = 12.dp,
) {
    val animationDurationMillis = 1500
    val waveDelayMillis = 100L
    val customEasing = LinearOutSlowInEasing

    val animatable = List(dotsCount) {
        remember { Animatable(initialValue = 0f) }
    }

    animatable.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable, key2 = customEasing, key3 = animationDurationMillis) {
            delay(index * waveDelayMillis)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDurationMillis
                        0.0f at 0 using customEasing
                        1.0f at (animationDurationMillis * 1 / 4) using customEasing
                        0.0f at (animationDurationMillis * 2 / 4) using customEasing
                        0.0f at (animationDurationMillis * 4 / 4) using customEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(spaceBetween),
        modifier = modifier
    ) {
        animatable.forEach { animatable ->
            Box(
                modifier = Modifier
                    .size(dotsSize)
                    .graphicsLayer {
                        translationY = -animatable.value * distance
                    }
                    .background(color = dotsColor, shape = CircleShape)
            )
        }
    }
}