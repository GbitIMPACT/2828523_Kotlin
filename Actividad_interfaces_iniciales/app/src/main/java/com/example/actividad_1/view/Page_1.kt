package com.example.actividad_1.view;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen( navController: NavHostController ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0)),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Bienvenido a la Pantalla Principal",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3F51B5)
            )

            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green button
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp) // Larger padding
            ) {
                Text(text = "Registrate", fontSize = 20.sp, color = Color.White)
            }

            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green button
                modifier = Modifier.padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                ) // Larger padding
            ) {
                Text(
                    text = "Inicia sesion",
                    fontSize = 20.sp,
                    color = Color.White
                ) // Larger button text, white color
            }

            Button(
                onClick = { navController.navigate("info") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Green button
                modifier = Modifier.padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                ) // Larger padding
            ) {
                Text(
                    text = "Ir a Información",
                    fontSize = 20.sp,
                    color = Color.White
                ) // Larger button text, white color
            }
        }
    }
}
