package com.hatde.ass_kot1041.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hatde.ass_kot1041.ui.screen.cart.CartScreen
import com.hatde.ass_kot1041.ui.screen.detail.ProductDetailScreen
import com.hatde.ass_kot1041.ui.screen.favorite.FavoriteScreen
import com.hatde.ass_kot1041.ui.screen.home.HomeScreen
import com.hatde.ass_kot1041.ui.screen.profile.ProfileScreen
import com.hatde.ass_kot1041.viewmodel.CartViewModel
import com.hatde.ass_kot1041.viewmodel.ProductViewModel

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Create shared ViewModels
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route, modifier = modifier) {
        composable(BottomBarScreen.Home.route) { 
            HomeScreen(
                productViewModel = productViewModel,
                cartViewModel = cartViewModel
            ) 
        }
        composable(BottomBarScreen.Favorite.route) { FavoriteScreen() }
        composable(BottomBarScreen.Cart.route) { 
            CartScreen(cartViewModel = cartViewModel) 
        }
        composable("product_detail") { ProductDetailScreen() }
        composable(BottomBarScreen.Profile.route) { ProfileScreen() }
    }
}
