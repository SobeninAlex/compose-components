package ru.sobeninalex.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Buttons2_Medium14
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.GrayColor50

/** дефолтный футер для ботом шита [Сбросить] [Подтвердить] */
@Composable
fun DefaultFooter(
    resetEnabled: Boolean,
    mainColor: Color,
    onResetClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    Column {
        HorizontalLine()

        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubmitButton( /* Сбросить */
                modifier = Modifier.weight(1f),
                height = 40.dp,
                enabled = resetEnabled,
                containerColor = GrayColor10,
                disabledContainerColor = GrayColor10,
                contentColor = BlackColor,
                disabledContentColor = GrayColor50,
                onClick = onResetClick
            ) {
                Text(
                    text = "Сбросить",
                    style = Buttons2_Medium14
                )
            }

            SubmitButton( /* Подтвердить */
                modifier = Modifier.weight(1f),
                height = 40.dp,
                containerColor = mainColor,
                onClick = onConfirmClick
            ) {
                Text(
                    text = "Подтвердить",
                    style = Buttons2_Medium14
                )
            }
        }
    }
}

@Preview
@Composable
private fun DefaultFooterPreview() {
    DefaultFooter(
        resetEnabled = false,
        mainColor = Color.Blue,
        onResetClick = {},
        onConfirmClick = {}
    )
}