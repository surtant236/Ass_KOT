package com.hatde.ass_kot1041

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.hatde.ass_kot1041.ui.navigation.RootNavGraph
import com.hatde.ass_kot1041.ui.theme.Ass_KOT1041Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ass_KOT1041Theme {
                val navController = rememberNavController()
                RootNavGraph(navController)
            }
        }
    }
}