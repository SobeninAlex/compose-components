package ru.sobeninalex.compose.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    show: Boolean = true,
    color: Color,
) {
    Animate(
        modifier = modifier,
        show = show
    ) {
        CircularProgressIndicator(
            color = color,
        )
    }
}