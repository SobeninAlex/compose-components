package ru.sobeninalex.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.compose.theme.BlackColor
import ru.sobeninalex.compose.theme.GrayColor20
import ru.sobeninalex.compose.theme.GrayColor50
import ru.sobeninalex.compose.theme.WhiteColor

@Composable
fun DashedHorizontalLine(
    modifier: Modifier = Modifier,
    lineColor: Color = GrayColor20,
) {
    Box(
        modifier = modifier
            .drawBehind {
                val dashWidth = 2.dp.toPx()
                val dashGap = 2.dp.toPx()

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = lineColor
                        style = PaintingStyle.Stroke
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
                    }
                    val y = size.height / 2
                    canvas.drawLine(Offset(0f, y), Offset(size.width, y), paint)
                }
            }
    )
}

@Preview
@Composable
private fun DashedHorizontalLinePreview() {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(WhiteColor),
        contentAlignment = Alignment.Center
    ) {
        DashedHorizontalLine(
            modifier = Modifier.fillMaxWidth(),
            lineColor = BlackColor
        )
    }
}