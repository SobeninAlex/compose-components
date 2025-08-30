package ru.sobeninalex.compose.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.GrayColor20

@Composable
fun HorizontalLine(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    thickness: Dp = 1.dp,
    color: Color = GrayColor20,
) {
    if (isVisible) {
        HorizontalDivider(
            thickness = thickness,
            color = color,
            modifier = modifier.fillMaxWidth()
        )
    }
}
