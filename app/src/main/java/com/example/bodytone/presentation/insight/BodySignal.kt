package com.example.bodytone.presentation.insight

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodytone.dataClass.CircularItem
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BodySignal() {

    val data = listOf(
        CircularItem("Mood", 30f, Color(0xFFFFF1F1), Color(0xFFF4C3C4)),
        CircularItem("Bloating", 31f, Color(0xFFB4A8DA), Color(0xFFF5F2FF)),
        CircularItem("Fatigue", 21f, Color(0xFFE99597), Color(0xFFFFE4E4)),
        CircularItem("Acne", 17f, Color(0xFFECFFF9), Color(0xFF6E8C82))
    )

    Column(
    ) {
        Row() {
            Spacer(Modifier.width(8.dp))
            DmText("Symptom Trends", DmStyle.Bold, fontSize = 18.sp, color = Color.Black)
            Spacer(Modifier.weight(1f))
        }
        Row() {
            Spacer(Modifier.width(8.dp))
            DmText("Compared to last cycle", DmStyle.Regular, fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(2.dp))
        Row() {
            Spacer(Modifier.weight(1f))
            HollowCircularGraph(
                items = data,
                modifier = Modifier
                    .size(260.dp)
            )
            Spacer(Modifier.weight(1f))

        }

    }

}


@Composable
fun HollowCircularGraph(
    items: List<CircularItem>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 90f,
    donutScale: Float = 0.75f,
    labelRadiusFactor: Float = 1.12f
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize().padding(bottom = 15.dp)) {

            val total = items.sumOf { it.percentage.toDouble() }.toFloat()
            var startAngle = -90f

            val radius = size.minDimension / 2 * donutScale

            items.forEach { item ->

                val sweepAngle = (item.percentage / total) * 360f

                drawArc(
                    brush = Brush.linearGradient(
                        colors = listOf(item.startColor, item.endColor)
                    ),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Butt
                    ),
                    size = Size(radius * 2, radius * 2),
                    topLeft = Offset(
                        (size.width - radius * 2) / 2,
                        (size.height - radius * 2) / 2
                    )
                )

                startAngle += sweepAngle
            }
        }

        // -------------------------------
        // 🔹 LABELS
        // -------------------------------
        BoxWithConstraints {

            val radius = constraints.maxWidth / 2 * donutScale

            var startAngle = -90f
            val total = items.sumOf { it.percentage.toDouble() }.toFloat()

            items.forEach { item ->

                val sweep = (item.percentage / total) * 360f
                val midAngle = startAngle + sweep / 2

                val angleRad = Math.toRadians(midAngle.toDouble())

                val x = cos(angleRad) * radius * labelRadiusFactor
                val y = sin(angleRad) * radius * labelRadiusFactor

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x.toInt(), y.toInt()) }
                        .size(60.dp)
                        .shadow(8.dp, shape = CircleShape, clip = false)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${item.percentage.toInt()}%", fontSize = 12.sp)
                        Text(item.label, fontSize = 10.sp)
                    }
                }

                startAngle += sweep
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun BodySignalPreview() {
    BodySignal()
}