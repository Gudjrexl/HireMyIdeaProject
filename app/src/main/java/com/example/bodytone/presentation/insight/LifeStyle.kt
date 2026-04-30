package com.example.bodytone.presentation.insight

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LifeStyle() {
    var selected by remember { mutableStateOf(4) }
    val sleepColors = listOf(
        Color(0xFFB39DDB) to Color(0xFF9575CD),
        Color(0xFFB39DDB) to Color(0xFF9575CD),
        Color(0xFFB39DDB) to Color(0xFF9575CD),
        Color(0xFFB39DDB) to Color(0xFF9575CD),
        Color(0xFFB39DDB) to Color(0xFF9575CD),
        Color(0xFFD1C4E9) to Color(0xFFB39DDB),
        Color(0xFFEDE7F6) to Color(0xFFD1C4E9),
        Color(0xFFF3F0FA) to Color(0xFFEDE7F6),
        Color(0xFFF8F6FC) to Color(0xFFF3F0FA)
    )

    val hydrateColors = listOf(
        Color(0xFFE57373) to Color(0xFFEF9A9A),
        Color(0xFFE57373) to Color(0xFFEF9A9A),
        Color(0xFFE57373) to Color(0xFFEF9A9A),
        Color(0xFFF8BDBD) to Color(0xFFFAD4D4),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA)
    )

    val caffeineColors = listOf(
        Color(0xFF80CBC4) to Color(0xFF4DB6AC),
        Color(0xFF80CBC4) to Color(0xFF4DB6AC),
        Color(0xFF80CBC4) to Color(0xFF4DB6AC),
        Color(0xFF80CBC4) to Color(0xFF4DB6AC),
        Color(0xFF80CBC4) to Color(0xFF4DB6AC),
        Color(0xFFB2DFDB) to Color(0xFF80CBC4),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA)
    )

    val exerciseColors = listOf(
        Color(0xFFFFCDD2) to Color(0xFFEF9A9A),
        Color(0xFFFFCDD2) to Color(0xFFEF9A9A),
        Color(0xFFFFCDD2) to Color(0xFFEF9A9A),
        Color(0xFFFFCDD2) to Color(0xFFEF9A9A),
        Color(0xFFFFCDD2) to Color(0xFFEF9A9A),
        Color(0xFFF8BDBD) to Color(0xFFFAD4D4),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA),
        Color(0xFFE0E0E0) to Color(0xFFEAEAEA)
    )



    Column(Modifier.padding(8.dp)) {
        Row() {
            DmText("Correlation Strength", DmStyle.Bold, fontSize = 18.sp, color = Color.Black)
            Spacer(Modifier.weight(1f))
            MonthDropdown(
                selectedValue = selected,
                onValueChange = { selected = it }
            )
        }

        CorrelationRow(
            title = "Sleep",
            colors = sleepColors
        )
        CorrelationRow("Hydrate", hydrateColors)
        CorrelationRow("Caffeine", caffeineColors)
        CorrelationRow("Exercise", exerciseColors)


    }
    }





@Composable
fun MonthDropdown(
    options: List<Int> = listOf(2, 4, 6),
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEAEAEA)) // light gray
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$selectedValue months",
                color = Color(0xFF333333), // dark gray
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xFFEAEAEA))
        ) {
            options.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "$value months",
                            color = Color(0xFF333333)
                        )
                    },
                    onClick = {
                        onValueChange(value)
                        expanded = false
                    }
                )
            }
        }
    }
}



@Composable
fun CorrelationRow(
    title: String,
    colors: List<Pair<Color, Color>>, // 9 gradient pairs
    modifier: Modifier = Modifier
) {
    require(colors.size == 9) { "You must pass exactly 9 gradient color pairs" }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Left text
        Text(
            text = title,
            color = Color(0xFF333333),
            fontSize = 14.sp,
            modifier = Modifier.width(80.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Right shapes
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            colors.forEach { (start, end) ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(24.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(start, end)
                            )
                        )
                )
            }
        }
    }
}




@Composable
@Preview(showBackground = true)
fun LifeStylepreview(){
    LifeStyle()
}