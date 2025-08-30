package ru.sobeninalex.compose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.R
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Body2_Regular14
import ru.sobeninalex.compose.theme.GrayColor20

@Composable
fun FilterApplyBlock(
    modifier: Modifier = Modifier,
    visibility: Boolean = true,
    countActiveFilter: Int,
    containerColor: Color,
    submitButtonColor: Color,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onSubmitClick: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visibility,
        enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
        ) {
            HorizontalLine()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Выбрано: $countActiveFilter",
                    color = BlackColor,
                    style = Body2_Regular14,
                    modifier = Modifier.padding(start = 4.dp)
                )

                SimpleSubmitButton(
                    text = "Применить",
                    containerColor = submitButtonColor,
                    onClick = onSubmitClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun FilterApplyBlockPreview() {
    FilterApplyBlock(
        countActiveFilter = 5,
        containerColor = GrayColor20,
        submitButtonColor = Color.Blue,
        onSubmitClick = {}
    )
}