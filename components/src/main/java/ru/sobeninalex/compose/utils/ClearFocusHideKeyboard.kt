package ru.sobeninalex.compose.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

fun Modifier.clearFocusHideKeyboard(): Modifier = composed {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    noRippleClickable {
        keyboardController?.hide()
        focusManager.clearFocus()
    }
}