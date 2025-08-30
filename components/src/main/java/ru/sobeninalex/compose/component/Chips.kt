package ru.sobeninalex.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Caption1_Medium12
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.roundedCornerShape20

@Composable
fun Chips(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = roundedCornerShape20,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    textStyle: TextStyle = Caption1_Medium12,
    textColor: Color = BlackColor,
    backgroundColor: Color = GrayColor10,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    clickEnabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    if (text.isBlank()) return
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickable(clickEnabled) { onClick() }
            .padding(paddingValues)
    ) {
        leadingIcon()
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            textAlign = TextAlign.Center
        )
        trailingIcon()
    }
}

@Preview
@Composable
private fun ChipsPreview() {
    Chips(
        text = "Chips"
    )
}