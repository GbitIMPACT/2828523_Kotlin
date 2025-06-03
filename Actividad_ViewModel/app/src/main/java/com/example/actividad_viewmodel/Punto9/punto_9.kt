package com.example.actividad_viewmodel.Punto9

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto9() {
    val viewModel: EmailValidacionViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Validación de Email",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de email con indicador
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.actualizarEmail(it) },
            label = { Text("Correo electrónico") },
            placeholder = { Text("ejemplo@correo.com") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            trailingIcon = {
                if (viewModel.mostrarIndicador) {
                    Text(
                        text = if (viewModel.esEmailValido) "✓" else "✗",
                        color = if (viewModel.esEmailValido) Color.Green else Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = when {
                    viewModel.email.isEmpty() -> MaterialTheme.colorScheme.primary
                    viewModel.esEmailValido -> Color.Green
                    else -> Color.Red
                },
                unfocusedBorderColor = when {
                    viewModel.email.isEmpty() -> MaterialTheme.colorScheme.outline
                    viewModel.esEmailValido -> Color.Green
                    else -> Color.Red
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Mensaje de estado
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = viewModel.mensajeEstado,
            color = when {
                viewModel.email.isEmpty() -> Color.Gray
                viewModel.esEmailValido -> Color.Green
                else -> Color.Red
            },
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card con información de validación
        Card(
            colors = CardDefaults.cardColors(
                containerColor = when {
                    viewModel.email.isEmpty() -> MaterialTheme.colorScheme.surfaceVariant
                    viewModel.esEmailValido -> Color.Green.copy(alpha = 0.1f)
                    else -> Color.Red.copy(alpha = 0.1f)
                }
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Estado de validación:",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Lista de requisitos
                RequisitoItem("Contiene @", viewModel.email.contains("@"))
                RequisitoItem("Contiene punto", viewModel.email.contains("."))
                RequisitoItem("@ antes del punto",
                    viewModel.email.contains("@") && viewModel.email.contains(".") &&
                            viewModel.email.indexOf("@") < viewModel.email.lastIndexOf(".")
                )
                RequisitoItem("Sin espacios", !viewModel.email.contains(" "))
                RequisitoItem("Formato completo", viewModel.esEmailValido)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón de ejemplo
            OutlinedButton(
                onClick = { viewModel.actualizarEmail("usuario@ejemplo.com") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Ejemplo válido")
            }

            // Botón limpiar
            Button(
                onClick = { viewModel.limpiarEmail() },
                enabled = viewModel.email.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Limpiar")
            }
        }
    }
}

@Composable
fun RequisitoItem(
    texto: String,
    cumplido: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (cumplido) "✓" else "✗",
            color = if (cumplido) Color.Green else Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.width(24.dp)
        )
        Text(
            text = texto,
            color = if (cumplido) Color.Green else Color.Gray,
            fontSize = 14.sp
        )
    }
}