package com.hatde.ass_kot1041.ui.navigation

sealed class BottomBarScreen(val route: String, val title: String, val icon: String = "") {
    object Home : BottomBarScreen("home", "Home")
    object Favorite : BottomBarScreen("favorite", "Favorite")
    object Cart : BottomBarScreen("cart", "Cart")
    object Profile : BottomBarScreen("profile", "Profile")
}

object NavigationRoutes {
    const val PRODUCT_MANAGEMENT = "product_management"
}