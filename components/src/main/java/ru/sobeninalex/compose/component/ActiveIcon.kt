package ru.sobeninalex.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.R
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.WhiteColor

@Composable
fun ActiveIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    isActive: Boolean,
    tint: Color,
    badgeTint: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = tint,
        )
        if (isActive) {
            Icon(
                painter = painterResource(id = R.drawable.ic_badge_24),
                contentDescription = null,
                tint = badgeTint,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ActiveIconPreview() {
    ActiveIcon(
        painter = painterResource(R.drawable.ic_notification_fill_24),
        isActive = true,
        tint = Color.Blue,
        badgeTint = Color.Yellow
    )
}