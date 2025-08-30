package ru.sobeninalex.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.utils.noRippleClickable
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Body2_Regular14
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.WhiteColor
import ru.sobeninalex.compose.theme.roundedCornerShape12

@Composable
fun FilterChips(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = Body2_Regular14,
    isActive: Boolean,
    backgroundColorActive: Color,
    backgroundColorNonActive: Color = GrayColor10,
    textColorActive: Color = WhiteColor,
    textColorNonActive: Color = BlackColor,
    onClick: () -> Unit
) {
    val textColor = if (isActive) textColorActive else textColorNonActive
    val backgroundColor = if (isActive) backgroundColorActive else backgroundColorNonActive
    Text(
        text = text,
        style = textStyle,
        color = textColor,
        modifier = modifier
            .clip(roundedCornerShape12)
            .background(backgroundColor)
            .noRippleClickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

@Preview
@Composable
private fun FilterChipsPreview() {
    Column(
        modifier = Modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChips(
            text = "Активный",
            isActive = true,
            backgroundColorActive = Color.Blue,
            onClick = {}
        )

        FilterChips(
            text = "НЕ активный",
            isActive = false,
            backgroundColorActive = Color.Blue,
            onClick = {}
        )
    }
}