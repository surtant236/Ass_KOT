package com.hatde.ass_kot1041.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.hatde.ass_kot1041.model.Product
import com.hatde.ass_kot1041.ui.screen.cart.CartScreen
import com.hatde.ass_kot1041.ui.screen.detail.ProductDetailScreen
import com.hatde.ass_kot1041.ui.screen.favorite.FavoriteScreen
import com.hatde.ass_kot1041.ui.screen.home.HomeScreen
import com.hatde.ass_kot1041.ui.screen.profile.ProfileScreen
import com.hatde.ass_kot1041.viewmodel.CartViewModel
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun NavGraph(
    navController: NavHostController, 
    modifier: Modifier = Modifier
) {
    val cartViewModel: CartViewModel = viewModel()
    
    NavHost(
        navController = navController, 
        startDestination = BottomBarScreen.Home.route, 
        modifier = modifier
    ) {
        composable(BottomBarScreen.Home.route) { 
            HomeScreen(
                onProductClick = { product ->
                    val productJson = URLEncoder.encode(Gson().toJson(product), "UTF-8")
                    navController.navigate("product_detail/$productJson")
                },
                onCartClick = {
                    navController.navigate(BottomBarScreen.Cart.route)
                }
            ) 
        }
        
        composable(BottomBarScreen.Favorite.route) { 
            FavoriteScreen() 
        }
        
        composable(BottomBarScreen.Cart.route) { 
            CartScreen(cartViewModel = cartViewModel) 
        }
        
        composable("product_detail/{productJson}") { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = productJson?.let { 
                val decodedJson = URLDecoder.decode(it, "UTF-8")
                try {
                    Gson().fromJson(decodedJson, Product::class.java)
                } catch (e: Exception) {
                    null
                }
            }
            ProductDetailScreen(
                product = product,
                onBackClick = { navController.popBackStack() },
                cartViewModel = cartViewModel
            )
        }
        
        composable(BottomBarScreen.Profile.route) { 
            ProfileScreen() 
        }
    }
}
