package com.klmapp

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.baseline_home_24, "Home")
    object Booking : NavigationItem("booking", R.drawable.baseline_calendar_month_24, "Booking")
}
