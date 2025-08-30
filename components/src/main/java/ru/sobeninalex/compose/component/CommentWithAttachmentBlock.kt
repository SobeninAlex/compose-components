package ru.sobeninalex.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.R
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Body2_Regular14
import ru.sobeninalex.compose.theme.GrayColor
import ru.sobeninalex.compose.theme.Title3_Bold16
import ru.sobeninalex.compose.utils.noRippleClickable

@Composable
fun CommentWithAttachmentBlock(
    modifier: Modifier = Modifier,
    contePadding: PaddingValues = PaddingValues(16.dp),
    comment: String,
    title: String,
    titleStyle: TextStyle = Title3_Bold16,
    titleColor: Color = BlackColor,
    placeholder: String,
    isRequired: Boolean = false,
    maxCommentLength: Int,
    showError: Boolean = false,
    minLength: Int = 5,
    error: String = "",
    errorTextColor: Color = Color.Red,
    onCommentChange: (String) -> Unit,
    onClickAttachRules: (() -> Unit)? = null,
    attachmentContent: @Composable () -> Unit
) {
    RoundedColumn(
        modifier = modifier,
        contentPadding = contePadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = titleStyle,
                color = titleColor,
            )

            if (isRequired) {
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(10.dp)
                        .align(Alignment.Top),
                    painter = painterResource(id = R.drawable.ic_asterisk_fill_18_black),
                    contentDescription = null,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        EditTextCounter(
            value = comment,
            onValueChange = onCommentChange,
            maxLength = maxCommentLength,
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            error = error,
            showError = showError,
            errorTextColor = errorTextColor,
            minLength = minLength,
            isRequired = isRequired,
            placeholder = placeholder
        )

        attachmentContent()

        onClickAttachRules?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .noRippleClickable { it() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Правила отправки файлов",
                    style = Body2_Regular14,
                    color = GrayColor
                )

                Image(
                    modifier = Modifier.padding(start = 6.dp),
                    painter = painterResource(id = R.drawable.ic_info_fill_18_gray50),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommentsBlockPreview() {
    CommentWithAttachmentBlock(
        title = "Заголовок блока",
        placeholder = "Placeholder",
        comment = "",
        error = "минимум 20 символов",
        onCommentChange = {},
        onClickAttachRules = {},
        attachmentContent = {},
        isRequired = true,
        maxCommentLength = 1000
    )
}