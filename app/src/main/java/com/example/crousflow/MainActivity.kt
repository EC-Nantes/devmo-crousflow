package com.example.crousflow

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "accueil") {
                composable("accueil") { AccueilScreen(navController) }
                composable("filtre") { FiltreScreen(navController) }
                composable("ficheRU/{ruName}") { backStackEntry ->
                    FicheRUScreen(
                        navController = navController,
                        ruName = backStackEntry.arguments?.getString("ruName") ?: ""
                    )
                }
            }
        }
    }
}


