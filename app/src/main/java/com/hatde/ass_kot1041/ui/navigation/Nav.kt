package com.hatde.ass_kot1041.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hatde.ass_kot1041.ui.screen.cart.CartScreen
import com.hatde.ass_kot1041.ui.screen.detail.ProductDetailScreen
import com.hatde.ass_kot1041.ui.screen.favorite.FavoriteScreen
import com.hatde.ass_kot1041.ui.screen.home.HomeScreen
import com.hatde.ass_kot1041.ui.screen.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        composable(BottomBarScreen.Home.route) { HomeScreen() }
        composable(BottomBarScreen.Favorite.route) { FavoriteScreen() }
        composable(BottomBarScreen.Cart.route) { CartScreen() }
        // Sửa route để nhận productId
        composable(
            route = "product_detail/{productId}"
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(productId = productId)
        }
        composable(BottomBarScreen.Profile.route) { ProfileScreen() }
    }
}
