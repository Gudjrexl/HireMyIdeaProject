package com.example.bodytone.presentation.insight

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.bodytone.R

enum class DmStyle {
    Regular,
    Medium,
    SemiBold,
    Bold
}


val DmSans = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_semibold, FontWeight.SemiBold),
    Font(R.font.dm_sans_bold, FontWeight.Bold)
)


@Composable
fun DmText(
    text: String,
    style: DmStyle = DmStyle.Bold,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
    color: Color = Color.Black
) {

    val fontWeight = when (style) {
        DmStyle.Regular -> FontWeight.Normal
        DmStyle.Medium -> FontWeight.Medium
        DmStyle.SemiBold -> FontWeight.SemiBold
        DmStyle.Bold -> FontWeight.Bold
    }

    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontFamily = DmSans,
        fontWeight = fontWeight,
        fontSize = fontSize,
        lineHeight = fontSize,
        letterSpacing = (-0.02).em
    )
}