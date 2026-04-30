package com.example.bodytone.presentation.insight


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bodytone.dataClass.MonthData

@Composable
fun Cycletrend() {

    val monthsData = listOf(
        MonthData("Jan", 5..10, 23..28, 30),
        MonthData("Feb", 4..9, 22..27, 27,),
        MonthData("Mar", 6..11, 24..29, 30),
        MonthData("Apr", 5..10, 23..28, 32),
        MonthData("May", 7..12, 21..26, 30),
        MonthData("Jun", 3..8, 20..25, 26),
        MonthData("Jul", 5..10, 23..28, 32),
        MonthData("Aug", 6..11, 24..29, 29),
        MonthData("Sep", 5..10, 23..28, 30),
        MonthData("Oct", 4..9, 22..27, 30),
        MonthData("Nov", 5..10, 23..28, 28),
        MonthData("Dec", 6..11, 24..29, 30)
    )

    var startIndex by remember { mutableStateOf(0) }

    val visibleMonths = monthsData.subList(
        startIndex,
        (startIndex + 6).coerceAtMost(monthsData.size)
    )

    val canGoLeft = startIndex > 0
    val canGoRight = startIndex + 6 < monthsData.size

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        // ⬅ LEFT ARROW
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (canGoLeft) Color.LightGray else Color.Transparent,
                    shape = CircleShape
                )
                .clickable(enabled = canGoLeft) { startIndex-- },
            contentAlignment = Alignment.Center
        ) {
            Text("<", color = if (canGoLeft) Color.Black else Color.Gray)
        }

        // 🔥 GRAPH AREA (center)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(260.dp),
            contentAlignment = Alignment.Center
        ) {

            // 🎯 DOTTED LINES
            Canvas(modifier = Modifier.matchParentSize()) {

                val lineColor = Color.LightGray
                val stroke = 2.dp.toPx()
                val dash = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                // middle
                drawLine(
                    color = lineColor,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = stroke,
                    pathEffect = dash
                )
                // bottom (fixed)
                drawLine(
                    color = lineColor,
                    start = Offset(0f, size.height * 0.83f),
                    end = Offset(size.width, size.height * 0.83f),
                    strokeWidth = stroke,
                    pathEffect = dash
                )
            }

            // 🔥 MONTHS
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                visibleMonths.forEach { data ->

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = data.totalDays.toString(),
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(4.dp))

                        MonthBar(
                            data = data,
                            modifier = Modifier.width(20.dp)
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(data.name)
                    }
                }
            }
        }

        // ➡ RIGHT ARROW
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (canGoRight) Color.LightGray else Color.Transparent,
                    shape = CircleShape
                )
                .clickable(enabled = canGoRight) { startIndex++ },
            contentAlignment = Alignment.Center
        ) {
            Text(">", color = if (canGoRight) Color.Black else Color.Gray)
        }
    }


}



@Composable
fun MonthBar(
    data: MonthData,
    modifier: Modifier = Modifier,
    height: Dp = 170.dp
) {
    val density = LocalDensity.current
    val heightPx = with(density) { height.toPx() }

    fun dayToY(day: Int): Float {
        return heightPx * (1f - (day / data.totalDays.toFloat()))
    }

    Box(
        modifier = modifier
            .height(height)
            .background(
                Color(0xFFB4A8DA),
                shape = RoundedCornerShape(10.dp)
            )
    ) {

        // 🔶 DULL RANGE (orange)
        val dullStart = dayToY(data.dullRange.first)
        val dullEnd = dayToY(data.dullRange.last)

        val dullTop = minOf(dullStart, dullEnd)
        val dullBottom = maxOf(dullStart, dullEnd)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { (dullBottom - dullTop).toDp() })
                .offset(y = with(density) { dullTop.toDp() })
                .background(Color(0xFFFFA726))
        )

        // 🟢 BRIGHT RANGE (green)
        val brightStart = dayToY(data.brightRange.first)
        val brightEnd = dayToY(data.brightRange.last)

        val brightTop = minOf(brightStart, brightEnd)
        val brightBottom = maxOf(brightStart, brightEnd)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { (brightBottom - brightTop).toDp() })
                .offset(y = with(density) { brightTop.toDp() })
                .background(Color(0xFF66BB6A))
        )

        // 💧 ICON (dull center)
        val dullCenter = (data.dullRange.first + data.dullRange.last) / 2f
        val dullY = dayToY(dullCenter.toInt())

        Text(
            text = "💧",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = with(density) { dullY.toDp() - 10.dp })
        )

        // ⚙️ ICON (bright center)
        val brightCenter = (data.brightRange.first + data.brightRange.last) / 2f
        val brightY = dayToY(brightCenter.toInt())

        Icon(
            Icons.Default.Settings
            , contentDescription = "Settings",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = with(density) { brightY.toDp() - 10.dp })
        )

    }
}


