package com.example.nuevas_cosas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

// import com.example.nuevas_cosas.ui.theme.Nuevas_CosasTheme // Si tienes un tema específico

@Composable
fun Pagina3( navController: NavHostController ) {
    Box(
        modifier = Modifier.fillMaxSize(), // El Box ocupa toda la pantalla
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        Text(text = "¡Hola Mundo!")
    }
}
