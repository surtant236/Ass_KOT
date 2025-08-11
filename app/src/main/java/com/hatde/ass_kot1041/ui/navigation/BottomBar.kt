package com.hatde.ass_kot1041.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Triple(BottomBarScreen.Home, Icons.Default.Home, "Home"),
        Triple(BottomBarScreen.Favorite, Icons.Default.Favorite, "Favorite"),
        Triple(BottomBarScreen.Cart, Icons.Default.ShoppingCart, "Cart"),
        Triple(BottomBarScreen.Profile, Icons.Default.Person, "Profile")

    )

    NavigationBar(containerColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { (screen, icon, _) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(BottomBarScreen.Home.route)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
