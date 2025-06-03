package com.example.actividad_viewmodel.Punto3
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Punto3() {
    val viewModel: PhoneViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Validador de Teléfono",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = viewModel.phoneNumber,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = { Text("Número de teléfono") },
            placeholder = { Text("Ingresa 10 dígitos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = getTextFieldColors(viewModel.isValid),
            modifier = Modifier.fillMaxWidth()
        )

        // Mostrar contador de dígitos
        Text(
            text = "${viewModel.phoneNumber.length}/10 dígitos",
            fontSize = 14.sp,
            color = when {
                viewModel.phoneNumber.length == 10 -> Color.Green
                viewModel.phoneNumber.length > 10 -> Color.Red
                else -> Color.Gray
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.validationMessage.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (viewModel.isValid == true)
                        Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else
                        Color.Red.copy(alpha = 0.1f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = viewModel.validationMessage,
                    color = if (viewModel.isValid == true) Color(0xFF4CAF50) else Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { viewModel.validatePhoneNumber() },
                enabled = viewModel.phoneNumber.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Validar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                onClick = { viewModel.clearPhoneNumber() },
                enabled = viewModel.phoneNumber.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Limpiar")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "ℹ️ Información",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "• Solo se aceptan números\n" +
                            "• Debe tener exactamente 10 dígitos\n" +
                            "• El borde cambia de color segun la ",
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun getTextFieldColors(isValid: Boolean?): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = when (isValid) {
            true -> Color.Green
            false -> Color.Red
            null -> MaterialTheme.colorScheme.primary
        },
        unfocusedBorderColor = when (isValid) {
            true -> Color.Green
            false -> Color.Red
            null -> MaterialTheme.colorScheme.outline
        },
        focusedLabelColor = when (isValid) {
            true -> Color.Green
            false -> Color.Red
            null -> MaterialTheme.colorScheme.primary
        }
    )
}