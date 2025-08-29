package ru.sobeninalex.compose.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadRefreshLayout(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    loadDelay: Long = 0,
    hideContentOnRefresh: Boolean = false,
    defaultIndicatorColor: Color = Color(0xFF125687),
    refreshContent: @Composable BoxScope.() -> Unit = {},
    loadingContent: @Composable BoxScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = defaultIndicatorColor,
        )
    },
    content: @Composable BoxScope.() -> Unit
) {
    LoadLayout(
        modifier = modifier,
        isLoading = isLoading,
        animationMillis = loadDelay,
        loadingContent = loadingContent
    ) {
        PullRefreshLayout(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            hideContentOnRefresh = hideContentOnRefresh,
            refreshContent = refreshContent
        ) {
            content()
        }
    }
}