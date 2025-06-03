package com.example.actividad_viewmodel.Punto8

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto8() {
    val viewModel: TextoLimiteViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Campo con Límite",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = viewModel.texto,
            onValueChange = { viewModel.actualizarTexto(it) },
            label = { Text("Escribe tu mensaje") },
            placeholder = { Text("Máximo 100 caracteres...") },
            isError = viewModel.excedeLimite,
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (viewModel.excedeLimite) Color.Red else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (viewModel.excedeLimite) Color.Red else MaterialTheme.colorScheme.outline
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (viewModel.excedeLimite) {
                Text(
                    text = "⚠️ Límite excedido",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            } else {
                Text(
                    text = "Caracteres restantes: ${viewModel.caracteresRestantes}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }


            Text(
                text = viewModel.contadorTexto,
                color = viewModel.colorContador,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(viewModel.porcentajeUsado)
                    .background(
                        when {
                            viewModel.excedeLimite -> Color.Red
                            viewModel.porcentajeUsado >= 0.9f -> Color(0xFFFFA000)
                            else -> Color.Green
                        }
                    )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            colors = CardDefaults.cardColors(
                containerColor = when {
                    viewModel.excedeLimite -> Color.Red.copy(alpha = 0.1f)
                    viewModel.texto.length >= 90 -> Color(0xFFFFA000).copy(alpha = 0.1f)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = when {
                        viewModel.excedeLimite -> "❌ Has excedido el límite"
                        viewModel.texto.length >= 90 -> "⚠️ Te acercas al límite"
                        viewModel.texto.isEmpty() -> "Criterios:"
                        else -> "✅ Dentro del límite"
                    },
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Caracteres usados: ${viewModel.texto.length}\n" +
                            "• Límite máximo: 100\n" +
                            "• ${if (viewModel.excedeLimite) "Exceso" else "Disponibles"}: ${kotlin.math.abs(viewModel.caracteresRestantes)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.texto.isNotEmpty()) {
            OutlinedButton(
                onClick = { viewModel.limpiarTexto() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpiar texto")
            }
        }
    }
}