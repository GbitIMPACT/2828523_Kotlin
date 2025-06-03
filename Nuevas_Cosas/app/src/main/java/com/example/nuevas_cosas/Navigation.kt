package com.example.nuevas_cosas

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigate(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "form") {
        composable("home") {
            Home(navController)
        };composable(
        route = "segundapagina/{nombre}",
        arguments = listOf(navArgument("nombre") { type = NavType.StringType })
    ) { data ->
        val nombre = data.arguments?.getString("nombre")
        Pagina2(navController, nombre)
    };composable("tercerapagina") {
        Pagina3(navController)

    };composable("form") {
        Form(navController)
    }
    }
}

