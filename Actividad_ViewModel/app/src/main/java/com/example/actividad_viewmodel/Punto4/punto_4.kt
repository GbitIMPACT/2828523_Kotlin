package com.example.actividad_viewmodel.Punto4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto4() {
    val viewModel: ThemeViewModel = viewModel()
    MaterialTheme(
        colorScheme = if (viewModel.isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            ThemeContent(
                isDarkTheme = viewModel.isDarkTheme,
                onToggleTheme = { viewModel.toggleTheme() }
            )
        }
    }
}

@Composable
private fun ThemeContent(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = if (isDarkTheme) "🌙" else "☀️",
            fontSize = 72.sp
        )
        Text(
            text = if (isDarkTheme) "Modo Oscuro" else "Modo Claro",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = if (isDarkTheme)
                    "Tema oscuro activado"
                else
                    "Tema claro activado",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onToggleTheme,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isDarkTheme)
                    "Cambiar a Claro"
                else
                    "Cambiar a Oscuro",
                fontSize = 16.sp
            )
        }

    }
}