package com.example.actividad_1.view;

import android.telecom.Call
import androidx.compose.runtime.Composable;
import androidx.navigation.compose.NavHost;
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController;
import com.example.actividad_1.HorizontalPages
import com.example.project_1.view.ActivityOne.FirstScreen

@Composable
fun Navigate(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
            composable("home"){
                HorizontalPages(navController)
            };composable("login") {
                LoginScreen(navController)
            };composable("register") {
                RegisterScreen(navController)
            }
    }
}
