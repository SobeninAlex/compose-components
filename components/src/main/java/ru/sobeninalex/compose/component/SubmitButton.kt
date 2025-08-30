package ru.sobeninalex.compose.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.GrayColor20
import ru.sobeninalex.compose.theme.WhiteColor
import ru.sobeninalex.compose.theme.roundedCornerShape12

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    enabled: Boolean = true,
    containerColor: Color,
    contentColor: Color = WhiteColor,
    disabledContainerColor: Color = GrayColor20,
    disabledContentColor: Color = GrayColor10,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = roundedCornerShape12,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        onClick = { onClick() },
    ) {
        content()
    }
}