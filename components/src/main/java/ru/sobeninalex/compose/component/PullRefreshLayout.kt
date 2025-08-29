package ru.sobeninalex.compose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import ru.sobeninalex.compose.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullRefreshLayout(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    hideContentOnRefresh: Boolean = true,
    refreshContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    val indicatorColors: List<Color> = listOf(
        Color(0xFF125687),
        Color(0xFFAD251B),
        Color(0xFF209826),
        Color(0xFFD7A802),
    )

    val rotationDuration = 1332L
    var currentColorIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRefreshing, indicatorColors.size) {
        if (isRefreshing) {
            currentColorIndex = 0
            while (isActive) {
                delay(rotationDuration)
                currentColorIndex = (currentColorIndex + 1) % indicatorColors.size
            }
        } else {
            currentColorIndex = 0
        }
    }

    val currentIndicatorColor = if (isRefreshing) {
        indicatorColors[currentColorIndex]
    } else {
        Color(0xFF125687)
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                containerColor = WhiteColor,
                color = currentIndicatorColor,
            )
        }
    ) {
        if (hideContentOnRefresh) {
            AnimatedVisibility(
                visible = !isRefreshing,
                enter = fadeIn(tween(500)),
                exit = fadeOut(tween(500))
            ) {
                Box(modifier = modifier) {
                    content()
                }
            }

            AnimatedVisibility(
                visible = isRefreshing,
                enter = fadeIn(tween(500)),
                exit = fadeOut(tween(500))
            ) {
                Box(modifier = modifier) {
                    refreshContent()
                }
            }
        } else {
            Box(modifier = modifier) {
                content()
            }
        }
    }
}
