package com.example.bodytone.presentation.insight

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Metabolictrend() {
    var selected by remember { mutableStateOf("Monthly") }
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val weights = listOf(30f, 45f, 35f, 70f, 55f, 50f, 45f, 39f, 30f, 45f, 55f, 70f)

    Column(Modifier.padding(8.dp)) {


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
            ) {
                DmText("Your weight", DmStyle.Bold, fontSize = 18.sp, color = Color.Black)
                DmText("in kg", DmStyle.Regular, fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(Modifier.weight(1f))
            DayToggleButton(
                text = "Monthly",
                isSelected = selected == "Monthly",
                onClick = { selected = it }
            )
            Spacer(modifier = Modifier.width(4.dp))
            DayToggleButton(
                text = "Weekly",
                isSelected = selected == "Weekly",
                onClick = { selected = it }
            )
        }

        WeightGraph(months, weights)
    }

}


@Composable
fun WeightGraph(months: List<String>, weights: List<Float>) {

    val maxWeight = 75f
    val step = 25f
    val scrollState = rememberScrollState()

    Row {

        Column(
            modifier = Modifier
                .height(240.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in (maxWeight / step).toInt() downTo 1) {
                Text(
                    text = "${i * step}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Column(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {

            Canvas(
                modifier = Modifier
                    .width((months.size * 80).dp)
                    .height(240.dp)
                    .padding(top = 16.dp, start = 8.dp, end = 16.dp)
            ) {

                val width = size.width
                val height = size.height
                val xStep = width / (months.size - 1)

                for (i in 1..(maxWeight / step).toInt()) {
                    val value = i * step
                    val y = height - (value / maxWeight) * height

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 2f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    )
                }


                val strokePath = Path()
                val fillPath = Path()

                weights.forEachIndexed { i, value ->
                    val x = i * xStep
                    val y = height - (value / maxWeight) * height

                    if (i == 0) {
                        strokePath.moveTo(x, y)
                        fillPath.moveTo(x, y)
                    } else {
                        val prevX = (i - 1) * xStep
                        val prevY = height - (weights[i - 1] / maxWeight) * height
                        val controlX = (prevX + x) / 2

                        strokePath.cubicTo(controlX, prevY, controlX, y, x, y)
                        fillPath.cubicTo(controlX, prevY, controlX, y, x, y)
                    }
                }

                fillPath.lineTo(width, height)
                fillPath.lineTo(0f, height)
                fillPath.close()

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x66E57373),
                            Color.Transparent
                        )
                    )
                )

                drawPath(
                    path = strokePath,
                    color = Color(0xFFE57373),
                    style = Stroke(width = 4f)
                )

                weights.forEachIndexed { i, value ->
                    val x = i * xStep
                    val y = height - (value / maxWeight) * height

                    drawCircle(Color.White, 10f, Offset(x, y))
                    drawCircle(Color(0xFFE57373), 5f, Offset(x, y))
                }
            }

            Row(
                modifier = Modifier
                    .width((months.size * 80).dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                months.forEach {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        modifier = Modifier.width(80.dp)
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MetabolictrendPreview() {
    Metabolictrend()
}