package ru.sobeninalex.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.R
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Body2_Regular14
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.roundedCornerShape12

@Composable
fun CloseableChips(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = roundedCornerShape12,
    paddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    onCloseClick: () -> Unit = {},
    onChipClick: () -> Unit = {},
    textStyle: TextStyle = Body2_Regular14,
    textColor: Color = BlackColor,
    backgroundColor: Color = GrayColor10
) {
    Chips(
        modifier = modifier,
        text = text,
        shape = shape,
        paddingValues = paddingValues,
        textStyle = textStyle,
        textColor = textColor,
        backgroundColor = backgroundColor,
        clickEnabled = true,
        onClick = onChipClick,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_close_circle_24_black50),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 4.dp)
                    .clickable { onCloseClick() }
            )
        }
    )
}

@Preview
@Composable
private fun CloseableChipsPreview() {
    CloseableChips(
        text = "ClosableChips",
    )
}