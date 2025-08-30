package ru.sobeninalex.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.WhiteColor
import ru.sobeninalex.compose.theme.roundedCornerShape20

@Composable
fun ClickableRoundedColumn(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClickEnabled: Boolean = true,
    backgroundColor: Color = WhiteColor,
    shape: Shape = roundedCornerShape20,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .clickable(enabled = isClickEnabled, onClick = onClick)
            .padding(contentPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
    }
}

@Preview
@Composable
private fun ClickableRoundedColumnPreview() {
    ClickableRoundedColumn(
        modifier = Modifier.height(150.dp),
        onClick = {},
    ) {}
}