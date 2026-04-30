package com.example.bodytone.presentation.insight


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun stabilityGraph(
    modifier: Modifier = Modifier,
    startDay: Int = 20,
    totalSteps: Int = 20
) {


    val months = listOf(
        "Jan","Feb","Mar","Apr","May"
    )
    val itemSpacing = 40.dp

    val days = remember {
        List(totalSteps) { startDay + (it * 4) }
    }
    val currentMonthIndex = remember {
//        java.time.LocalDate.now().monthValue - 1
        2
    }

    val yState = rememberLazyListState()
    val xScroll = rememberScrollState()
    val density = LocalDensity.current

    LaunchedEffect(xScroll.value) {
        val pxPerItem = with(density) { itemSpacing.toPx() }

        val index = (xScroll.value / pxPerItem).toInt()

        yState.scrollToItem(index.coerceIn(0, days.lastIndex))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    ) {

        val itemHeight = 40.dp
        val graphHeight = itemHeight * 3


        val startPaddingPx = with(density) { 55.dp.toPx() }

        val stepX = with(density) { itemSpacing.toPx() }

        val xPositionPx =
            startPaddingPx +
                    currentMonthIndex * stepX -
                    xScroll.value +
                    (stepX )


        val pointCount = 20

        val maxVisibleIndex =
            xScroll.value / with(density) { itemSpacing.toPx() }

        val progress =
            (currentMonthIndex + maxVisibleIndex) / pointCount.toFloat()

        val yPositionPx =
            with(density) { graphHeight.toPx() } *
                    (1f - (progress * progress))
        val xDp = with(density) { xPositionPx.toDp() }
        val yDp = with(density) { yPositionPx.toDp() }


        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(graphHeight)
                .align(Alignment.BottomStart)
                .padding(start = 34.dp, bottom = 18.dp)
        ) {

            val width = size.width
            val height = size.height

            val maxVisibleIndex =
                xScroll.value / with(density) { itemSpacing.toPx() }

            val pointCount = 20
            val stepX = width / (pointCount - 1)

            fun generatePoints(scale: Float): List<Offset> {
                return List(pointCount) { i ->
                    val progress = (i + maxVisibleIndex) / pointCount.toFloat()

                    val baseY = height * (1f - (progress * progress))

                    val scaledY = height - (height - baseY) * scale

                    Offset(i * stepX, scaledY)
                }
            }

            fun createFill(points: List<Offset>): Path {
                val path = Path()

                points.forEachIndexed { i, p ->
                    if (i == 0) path.moveTo(p.x, p.y)
                    else path.lineTo(p.x, p.y)
                }

                path.lineTo(width, height)
                path.lineTo(0f, height)
                path.close()

                return path
            }

            drawPath(
                createFill(generatePoints(1f)),
                Color(0xFFE6E0FB)
            )

            drawPath(
                createFill(generatePoints(0.66f)),
                Color(0xFFB4A8DA)
            )

            drawPath(
                createFill(generatePoints(0.33f)),
                Color(0xFFE6E0FB)
            )
            drawLine(
                color = Color.Gray,
                start = Offset(xPositionPx, 0f),
                end = Offset(xPositionPx, height),
                strokeWidth = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )

            // 🔥 PIN
            drawCircle(
                color = Color(0xFF4CAF50),
                radius = 8.dp.toPx(),
                center = Offset(xPositionPx, 5.dp.toPx())
            )
            val graphPath = createFill(generatePoints(1f))

            clipRect(
                left = 0f,
                top = 0f,
                right = xPositionPx,
                bottom = height
            ) {
                clipPath(graphPath) {
                    drawRect(
                        color = Color.Black.copy(alpha = 0.12f)
                    )
                }
            }

        }
        Text(
            text = "Stability Improving",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(
                    x = xDp - 70.dp,
                    y = graphHeight - yDp - 130.dp
                )
                .background(Color.Black)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )


        LazyColumn(
            state = yState,
            reverseLayout = true,
            modifier = Modifier
                .height(graphHeight)
                .align(Alignment.BottomStart)
        ) {
            items(days) { day ->
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text("$day d", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        val itemWidth = 60.dp

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .horizontalScroll(xScroll)
                .padding(start = 34.dp, top = graphHeight + 5.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            months.forEachIndexed { index, month ->
                Box(
                    modifier = Modifier.width(itemWidth),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = month,
                        color = if (index == currentMonthIndex)
                            Color.Black else Color.Gray
                    )
                }
            }
        }
    }
}


