package ru.sobeninalex.compose.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.sobeninalex.compose.theme.Caption2_Regular12

@Composable
fun AnimatedErrorText(
    modifier: Modifier = Modifier,
    errorText: String,
    errorTextColor: Color,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible && errorText.isNotBlank(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Text(
            text = errorText,
            style = Caption2_Regular12,
            color = errorTextColor,
            modifier = modifier
        )
    }
}
