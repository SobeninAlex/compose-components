package ru.sobeninalex.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.sobeninalex.compose.R
import ru.sobeninalex.compose.theme.Body1_Regular16
import ru.sobeninalex.compose.theme.Caption2_Regular12
import ru.sobeninalex.compose.theme.GrayColor
import ru.sobeninalex.compose.theme.GrayColor10
import ru.sobeninalex.compose.theme.GrayColor50
import ru.sobeninalex.compose.theme.roundedCornerShape12
import ru.sobeninalex.compose.utils.noRippleClickable

@Composable
fun EditTextCounter(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int = Int.MAX_VALUE,
    minLength: Int = 5,
    placeholder: String = "",
    error: String = "",
    errorTextColor: Color = Color.Red,
    showError: Boolean = false,
    isRequired: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = Body1_Regular16,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    closeIconVisibility: Boolean = true
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var fieldFocused by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    var internalTextFieldValue by remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    BasicTextField(
        value = internalTextFieldValue,
        onValueChange = {
            if (isValidValue(it.text, maxLength)) {
                onValueChange(it.text)
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onAny = {
                keyboardController?.hide()
                focusManager.clearFocus()
                fieldFocused = false
                keyboardActions.onDone
                keyboardActions.onSearch
                keyboardActions.onGo
                keyboardActions.onNext
                keyboardActions.onSend
                keyboardActions.onPrevious
            },
        ),
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = {
            val cursorRect = it.getCursorRect(internalTextFieldValue.selection.start)
            coroutineScope.launch {
                bringIntoViewRequester.bringIntoView(cursorRect)
            }
        },
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            val verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
            val topIconPadding = if (singleLine) 0.dp else 1.dp
            Row(
                verticalAlignment = verticalAlignment,
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (internalTextFieldValue.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = GrayColor50
                        )
                    }
                    innerTextField()
                }

                if (internalTextFieldValue.text.isNotEmpty() && closeIconVisibility) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_outline_18_gray70),
                        contentDescription = null,
                        modifier = modifier
                            .padding(top = topIconPadding)
                            .size(20.dp)
                            .noRippleClickable {
                                internalTextFieldValue = TextFieldValue("")
                                onValueChange("")
                            }
                    )
                }
            }
        },
        modifier = modifier
            .clip(roundedCornerShape12)
            .background(GrayColor10)
            .padding(12.dp)
    )

    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {
        AnimatedErrorText(
            errorText = error,
            errorTextColor = errorTextColor,
            isVisible = isRequired
                    && internalTextFieldValue.text.length < minLength
                    && (!fieldFocused || showError)
        )

        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = "${value.length} / $maxLength",
            style = Caption2_Regular12,
            textAlign = TextAlign.End,
            color = GrayColor,
        )
    }
}

private fun isValidValue(value: String, maxLength: Int): Boolean {
    return value.length <= maxLength
}