package com.example.workforserialization.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workforserialization.objects.RoutesNavigation
import com.example.workforserialization.screens.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RoutesNavigation.STARTSCREEN){

        composable(RoutesNavigation.STARTSCREEN){
            //startScreen(navHostController = navController)
        }

    }
}