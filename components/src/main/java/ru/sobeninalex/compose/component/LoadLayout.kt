package ru.sobeninalex.compose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun LoadLayout(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    animationMillis: Long = 0,
    defaultIndicatorColor: Color = Color(0xFF125687),
    loadingContent: @Composable BoxScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = defaultIndicatorColor,
        )
    },
    content: @Composable BoxScope.() -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }

    val showLoading = isLoading || animationStarted
    LaunchedEffect(key1 = showLoading) {
        animationStarted = isLoading
        delay(animationMillis)
        animationStarted = false
    }

    Box {
        AnimatedVisibility(
            visible = !showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box(modifier = modifier) {
                content()
            }
        }

        AnimatedVisibility(
            visible = showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box(modifier = modifier) {
                loadingContent()
            }
        }
    }
}