package com.example.bodytone.presentation.insight

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodytone.R

@Composable
fun InsightHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.frame),
                contentDescription = "iInsight Icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
        DmText("iInsight", modifier = Modifier.align(Alignment.Center))
    }
}




@Composable
fun SquareCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .aspectRatio(1f)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color.Black.copy(alpha = 0.08f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(
                width = 1.dp,
                color = Color(0x140D0A2C), // #0D0A2C14 (ARGB format)
                shape = RoundedCornerShape(cornerRadius)
            ),
        contentAlignment = Alignment.Center

    ) {
        content()
    }
}




@Composable
fun stabilitySummary(){
    Column(
        Modifier.padding(start = 8.dp, end = (8.dp))
    ) {
        DmText("Based on your recent logs and symptom patterns.",
            modifier = Modifier.padding(top = 8.dp),
            style = DmStyle.Regular, fontSize = 15.sp,
            color = Color(0xFF696770))
        Spacer(Modifier.height(12.dp))
        DmText("Stability Score",
            modifier = Modifier.padding(top = 3.dp),
            style = DmStyle.SemiBold, fontSize = 16.sp,
           )
        Spacer(Modifier.height(4.dp))
        Stabilitycalucation("para1", "param2")
        stabilityGraph()
    }
}





@Composable
fun Stabilitycalucation(para1: String, para2: String) {
    DmText("78 %",
        style = DmStyle.Bold, fontSize = 19.sp,
        )
}




@Composable
fun DayToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    Button(
        onClick = { onClick(text) },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color.LightGray,
            contentColor = if (isSelected) Color.White else Color.Black
        )
    ) {
        Text(text)
    }}




