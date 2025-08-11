package com.hatde.ass_kot1041.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hatde.ass_kot1041.ui.navigation.BottomBar
import com.hatde.ass_kot1041.ui.navigation.NavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        NavGraph(navController = navController, modifier = Modifier.padding(padding))
    }
}
