package com.example.bodytone.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bodytone.presentation.home.HomeScreen
import com.example.bodytone.presentation.insight.InsightScreen
import com.example.bodytone.presentation.track.TrackScreen

@Composable
fun AllScreen() {
    val navController = rememberNavController()

    val pink = Color(0xFFE99597)
    val lightGreen = Color(0xFFEBF1EB)

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.White, pink)
                    )
                )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, lightGreen)
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = { CustomBottomBar(navController) }
        ) { padding ->

            NavHost(
                navController = navController,
                startDestination = "insight",
                modifier = Modifier
                    .padding(padding)
            ) {
                composable("home") { InsightScreen() }
                composable("track") {InsightScreen()  }
                composable("insight") { InsightScreen() }
            }
        }
    }
}