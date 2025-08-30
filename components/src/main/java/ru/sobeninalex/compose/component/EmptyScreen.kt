package ru.sobeninalex.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.R
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.Body2_Regular14
import ru.sobeninalex.compose.theme.GrayColor
import ru.sobeninalex.compose.theme.Title1_Bold20
import ru.sobeninalex.compose.theme.WhiteColor

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    titleStyle: TextStyle = Title1_Bold20,
    titleColor: Color = BlackColor,
    subtitle: String? = null,
    subtitleStyle: TextStyle = Body2_Regular14,
    subtitleColor: Color = GrayColor,
    actions: @Composable (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = title,
            style = titleStyle,
            color = titleColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        subtitle?.let {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = it,
                style = subtitleStyle,
                color = subtitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }

        actions?.let {
            Spacer(modifier = Modifier.height(8.dp))
            it()
        }
    }
}

@Preview
@Composable
private fun EmptyScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WhiteColor
    ) {
        EmptyScreen(
            painter = painterResource(R.drawable.img_empty_state),
            title = "Dolorem aut omnis dolorem temporibus et illum.",
            subtitle = "Non unde ut totam autem aliquid. Tempora architecto velit ea vitae natus. In non numquam sunt ut sequi. Officia quas voluptatem.",
        )
    }
}