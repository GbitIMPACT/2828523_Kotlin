package com.example.actividad_1.view;
import androidx.compose.foundation.background // Import for background styling
import androidx.compose.foundation.layout.Arrangement // For arranging elements within a Column or Row
import androidx.compose.foundation.layout.Box // A layout composable that positions its children relative to its edges
import androidx.compose.foundation.layout.Column // A layout composable that places its children in a vertical sequence
import androidx.compose.foundation.layout.Spacer // For adding space between elements
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available space
import androidx.compose.foundation.layout.height // Modifier to set the height of a composable
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable
import androidx.compose.foundation.layout.width // Modifier to set the width of a composable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // For customizing Button appearance
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable // Core Composable annotation
import androidx.compose.ui.Alignment // For aligning children within a parent composable
import androidx.compose.ui.Modifier // For applying modifications to composables
import androidx.compose.ui.graphics.Color // For defining colors
import androidx.compose.ui.text.font.FontWeight // For setting text font weight
import androidx.compose.ui.unit.dp // For specifying dimensions in density-independent pixels
import androidx.compose.ui.unit.sp // For specifying font sizes in scale-independent pixels
import androidx.navigation.NavHostController // For handling navigation

@Composable
fun Info( navController: NavHostController  ){
    Box(
        modifier = Modifier
            .fillMaxSize() // Fill the entire screen
            .background(Color(0xFFE0E0E0)), // Light gray background
        contentAlignment = Alignment.Center // Center content within the Box
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center children horizontally
            verticalArrangement = Arrangement.spacedBy(16.dp) // Add 16dp spacing between children
        ) {
            Text(
                text = "Welcome to the Info Page!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3F51B5)
            )
            Spacer(modifier = Modifier.height(24.dp)) // Add some extra space
            Button(
                onClick = { navController.navigate( "details" ) },
                modifier = Modifier.width(200.dp).height(50.dp), // Bigger button
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Ir a detalles", fontSize = 18.sp, color = Color.White)
            }
            Button( onClick = { navController.navigate( "home" ) }, modifier = Modifier.width(200.dp).height(50.dp) ) {
                Text(text = "Ir al Home", fontSize = 18.sp)
            }
        }
    }
}