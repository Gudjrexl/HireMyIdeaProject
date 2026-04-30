package com.example.bodytone.navigation

import com.example.bodytone.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object Track : BottomNavItem("track", R.drawable.track, "Track")
    object Insight : BottomNavItem("insight", R.drawable.insight, "iInsight")
}

