package ru.sobeninalex.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.Caption1_Medium12
import ru.sobeninalex.compose.theme.GrayColor
import ru.sobeninalex.compose.theme.WhiteColor
import ru.sobeninalex.compose.theme.roundedCornerShape20

@Composable
fun Bubble(
    text: String,
    textStyle: TextStyle = Caption1_Medium12,
    textColor: Color = WhiteColor,
    shape: Shape = roundedCornerShape20,
    backgroundColor: Color = GrayColor,
    paddingValues: PaddingValues = PaddingValues(vertical = 4.dp, horizontal = 8.dp)
) {
    Text(
        text = text,
        style = textStyle,
        color = textColor,
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor)
            .padding(paddingValues)
    )
}

@Preview
@Composable
private fun BubblePreview() {
    Bubble(
        text = "Active"
    )
}