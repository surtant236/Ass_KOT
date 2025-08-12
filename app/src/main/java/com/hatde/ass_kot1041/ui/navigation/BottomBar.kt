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
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Triple(BottomBarScreen.Home, Icons.Default.Home, "Trang chủ"),
        Triple(BottomBarScreen.Favorite, Icons.Default.Favorite, "Yêu thích"),
        Triple(BottomBarScreen.Cart, Icons.Default.ShoppingCart, "Giỏ hàng"),
        Triple(BottomBarScreen.Profile, Icons.Default.Person, "Hồ sơ")
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { (screen, icon, label) ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        icon, 
                        contentDescription = label,
                        tint = if (currentRoute == screen.route) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    ) 
                },
                label = { 
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (currentRoute == screen.route) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    ) 
                },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(BottomBarScreen.Home.route)
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
