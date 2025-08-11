package com.hatde.ass_kot1041.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hatde.ass_kot1041.ui.screen.auth.LoginScreen
import com.hatde.ass_kot1041.ui.screen.auth.RegisterScreen
import com.hatde.ass_kot1041.ui.screen.auth.SplashScreen
import com.hatde.ass_kot1041.ui.screen.MainScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("main") { MainScreen() }
    }
}