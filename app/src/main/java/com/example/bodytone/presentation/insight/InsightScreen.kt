package com.example.bodytone.presentation.insight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InsightScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row() {
            InsightHeader()
        }
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {

        Spacer(Modifier.height(10.dp))
        DmText("Stability Summary", modifier = Modifier.padding(start = 12.dp))
        Spacer(Modifier.height(10.dp))
        SquareCard {
            stabilitySummary()
        }
        Spacer(Modifier.height(10.dp))


        DmText("Cycle Trends", modifier = Modifier.padding(start = 12.dp))
        Spacer(Modifier.height(10.dp))
        SquareCard {
            Cycletrend()
        }
        Spacer(Modifier.height(10.dp))


        DmText("Body & Metabolic Trends", modifier = Modifier.padding(start = 12.dp))
        Spacer(Modifier.height(10.dp))
        SquareCard {
            Metabolictrend()
        }
        Spacer(Modifier.height(10.dp))


        DmText("Body Signals", modifier = Modifier.padding(start = 12.dp))
        Spacer(Modifier.height(10.dp))
        SquareCard {
            BodySignal()
        }
        Spacer(Modifier.height(10.dp))



        DmText("Lifestyle Impact", modifier = Modifier.padding(start = 12.dp))
        Spacer(Modifier.height(10.dp))
        SquareCard {
            LifeStyle()
        }
        Spacer(Modifier.height(10.dp))

    }

}}







