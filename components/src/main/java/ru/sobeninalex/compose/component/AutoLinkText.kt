package ru.sobeninalex.compose.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Body2_Regular14
import ru.sobeninalex.compose.theme.WhiteColor

@Composable
fun AutoLinkText(
    text: String,
    style: TextStyle = Body2_Regular14,
    color: Color = BlackColor,
    linkColor: Color = Color.Blue,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Visible,
    phoneCode: String = "+7",
    phoneLength: Int = 10,
) {
    val context: Context = LocalContext.current
    val onLinkClick: (String) -> Unit = { link ->
        when {
            link.startsWith(phoneCode) && link.matches("\\$phoneCode[0-9]{$phoneLength}".toRegex()) -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, "tel:$link".toUri())
                context.startActivity(dialIntent)
            }
            link.contains("@") -> {
                val mailIntent = Intent(Intent.ACTION_SENDTO, "mailto:$link".toUri())
                context.startActivity(mailIntent)
            }
            link.startsWith("http") || link.startsWith("www.") || link.matches("\\b[\\w\\-._~]+\\.[a-zA-Z]{2,}\\b".toRegex()) -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(if (link.startsWith("http")) link else "https://$link"))
                context.startActivity(browserIntent)
            }
            else -> {
                Toast.makeText(context, "Невозможно открыть ссылку", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val annotatedText = buildAnnotatedString {
        val regex = "(https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+|www\\.[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=%]+|\\b[\\w\\-._~]+\\.[a-zA-Z]{2,}\\b|\\b\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,}\\b|\\$phoneCode[0-9]{$phoneLength}|\\+?[0-9]{7,15})".toRegex()
        var lastIndex = 0

        regex.findAll(text).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last + 1
            val match = matchResult.value

            if (lastIndex < start) {
                append(text.substring(lastIndex, start))
            }

            if (match.startsWith(phoneCode) && match.matches("\\$phoneCode[0-9]{$phoneLength}".toRegex())) {
                pushStringAnnotation(tag = "URL", annotation = match)
                withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                    append(match)
                }
                pop()
            } else if (match.startsWith("http") || match.startsWith("www.") || match.matches("\\b[\\w\\-._~]+\\.[a-zA-Z]{2,}\\b".toRegex())) {
                pushStringAnnotation(tag = "URL", annotation = if (match.startsWith("http")) match else "http://$match")
                withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                    append(match)
                }
                pop()
            } else if (match.contains("@")) {
                pushStringAnnotation(tag = "URL", annotation = match)
                withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                    append(match)
                }
                pop()
            } else {
                append(match)
            }

            lastIndex = end
        }

        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    ClickableText(
        text = annotatedText,
        style = style.copy(color = color),
        maxLines = maxLines,
        overflow = overflow,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onLinkClick(annotation.item)
                }
        }
    )
}

@Preview
@Composable
private fun AutoLinkTextPreview() {
    Surface(
        color = WhiteColor
    ) {
        AutoLinkText(
            text = """
            Modi pariatur tenetur.
            +79999999999
            https://ya.ru
            Excepturi odit quasi sequi perspiciatis pariatur aut. 
            http://ya.ru,
            test@test.com
            Perferendis maiores quia molestiae eius.
        """.trimIndent(),
        )
    }
}